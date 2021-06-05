package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v340.serializer.AvailableCommandsSerializer_v340;
import org.cloudburstmc.protocol.bedrock.data.command.*;
import org.cloudburstmc.protocol.bedrock.packet.AvailableCommandsPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AvailableCommandsSerializer_v388 extends AvailableCommandsSerializer_v340 {

    public AvailableCommandsSerializer_v388(TypeMap<CommandParam> paramTypeMap) {
        super(paramTypeMap);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableCommandsPacket packet) {
        Set<String> enumValuesSet = new ObjectOpenHashSet<>();
        Set<String> postfixSet = new ObjectOpenHashSet<>();
        Set<CommandEnumData> enumsSet = new ObjectOpenHashSet<>();
        Set<CommandEnumData> softEnumsSet = new ObjectOpenHashSet<>();

        // Get all enum values
        for (CommandData data : packet.getCommands()) {
            if (data.getAliases() != null) {
                Collections.addAll(enumValuesSet, data.getAliases().getValues());
                enumsSet.add(data.getAliases());
            }

            for (CommandParamData[] overload : data.getOverloads()) {
                for (CommandParamData parameter : overload) {
                    CommandEnumData commandEnumData = parameter.getEnumData();
                    if (commandEnumData != null) {
                        if (commandEnumData.isSoft()) {
                            softEnumsSet.add(commandEnumData);
                        } else {
                            Collections.addAll(enumValuesSet, commandEnumData.getValues());
                            enumsSet.add(commandEnumData);
                        }
                    }

                    String postfix = parameter.getPostfix();
                    if (postfix != null) {
                        postfixSet.add(postfix);
                    }
                }
            }
        }

        // Add Constraint Enums
        for(CommandEnumData enumData : packet.getConstraints().stream().map(CommandEnumConstraintData::getEnumData).collect(Collectors.toList())) {
            if (enumData.isSoft()) {
                softEnumsSet.add(enumData);
            } else {
                enumsSet.add(enumData);
            }
            enumValuesSet.addAll(Arrays.asList(enumData.getValues()));
        }

        List<String> enumValues = new ObjectArrayList<>(enumValuesSet);
        List<String> postFixes = new ObjectArrayList<>(postfixSet);
        List<CommandEnumData> enums = new ObjectArrayList<>(enumsSet);
        List<CommandEnumData> softEnums = new ObjectArrayList<>(softEnumsSet);

        helper.writeArray(buffer, enumValues, helper::writeString);
        helper.writeArray(buffer, postFixes, helper::writeString);

        this.writeEnums(buffer, helper, enumValues, enums);

        helper.writeArray(buffer, packet.getCommands(), (buf, command) -> {
            this.writeCommand(buffer, helper, command, enums, softEnums, postFixes);
        });

        helper.writeArray(buffer, softEnums, helper::writeCommandEnum);

        // Constraints
        helper.writeArray(buffer, packet.getConstraints(), (buf, constraint) -> {
            helper.writeCommandEnumConstraints(buf, constraint, enums, enumValues);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableCommandsPacket packet) {
        List<String> enumValues = new ObjectArrayList<>();
        List<String> postFixes = new ObjectArrayList<>();
        List<CommandEnumData> enums = new ObjectArrayList<>();
        List<CommandData.Builder> commands = new ObjectArrayList<>();
        List<CommandEnumData> softEnums = new ObjectArrayList<>();

        helper.readArray(buffer, enumValues, helper::readString);
        helper.readArray(buffer, postFixes, helper::readString);

        this.readEnums(buffer, helper, enumValues, enums);

        helper.readArray(buffer, commands, this::readCommand);

        helper.readArray(buffer, softEnums, buf -> helper.readCommandEnum(buffer, true));


        // Generate command data
        for (CommandData.Builder command : commands) {
            byte flags = command.getFlags();
            List<CommandData.Flag> flagList = new ObjectArrayList<>();
            for (int i = 0; i < 6; i++) {
                if ((flags & (1 << i)) != 0) {
                    flagList.add(FLAGS[i]);
                }
            }
            int aliasesIndex = command.getAliases();
            CommandEnumData aliases = aliasesIndex == -1 ? null : enums.get(aliasesIndex);

            CommandParamData.Builder[][] overloadBuilders = command.getOverloads();
            CommandParamData[][] overloads = new CommandParamData[overloadBuilders.length][];
            for (int i = 0; i < overloadBuilders.length; i++) {
                overloads[i] = new CommandParamData[overloadBuilders[i].length];
                for (int i2 = 0; i2 < overloadBuilders[i].length; i2++) {
                    CommandParamData.Builder param = overloadBuilders[i][i2];
                    String name = param.getName();
                    CommandSymbolData type = param.getType();
                    boolean optional = param.isOptional();
                    byte optionsByte = param.getOptions();

                    String postfix = null;
                    CommandEnumData enumData = null;
                    CommandParam commandParam = null;
                    if (type.isPostfix()) {
                        postfix = postFixes.get(type.getValue());
                    } else {
                        if (type.isCommandEnum()) {
                            enumData = enums.get(type.getValue());
                        } else if (type.isSoftEnum()) {
                            enumData = softEnums.get(type.getValue());
                        } else {
                            commandParam = helper.getCommandParam(type.getValue());
                        }
                    }

                    List<CommandParamOption> options = new ObjectArrayList<>();
                    for (int idx = 0; idx < 8; idx++) {
                        if ((optionsByte & (1 << idx)) != 0) {
                            options.add(OPTIONS[idx]);
                        }
                    }

                    overloads[i][i2] = new CommandParamData(name, optional, enumData, commandParam, postfix, options);
                }
            }

            packet.getCommands().add(new CommandData(command.getName(), command.getDescription(),
                    flagList, command.getPermission(), aliases, overloads));
        }

        // Constraints
        helper.readArray(buffer, packet.getConstraints(), buf -> helper.readCommandEnumConstraints(buf, enums, enumValues));
    }
}