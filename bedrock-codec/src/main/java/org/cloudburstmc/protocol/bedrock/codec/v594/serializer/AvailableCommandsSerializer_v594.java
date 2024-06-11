package org.cloudburstmc.protocol.bedrock.codec.v594.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongObjectPair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.AvailableCommandsSerializer_v448;
import org.cloudburstmc.protocol.bedrock.data.command.*;
import org.cloudburstmc.protocol.bedrock.packet.AvailableCommandsPacket;
import org.cloudburstmc.protocol.common.util.LongKeys;
import org.cloudburstmc.protocol.common.util.SequencedHashSet;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

public class AvailableCommandsSerializer_v594 extends AvailableCommandsSerializer_v448 {

    public AvailableCommandsSerializer_v594(TypeMap<CommandParam> paramTypeMap) {
        super(paramTypeMap);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableCommandsPacket packet) {
        SequencedHashSet<String> enumValues = new SequencedHashSet<>();
        SequencedHashSet<String> subCommandValues = new SequencedHashSet<>();
        SequencedHashSet<String> postFixes = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> enums = new SequencedHashSet<>();
        SequencedHashSet<ChainedSubCommandData> subCommandData = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> softEnums = new SequencedHashSet<>();
        SequencedHashSet<LongObjectPair<Set<CommandEnumConstraint>>> enumConstraints = new SequencedHashSet<>();

        // Get all enum values
        for (CommandData data : packet.getCommands()) {
            if (data.getAliases() != null) {
                enumValues.addAll(data.getAliases().getValues().keySet());
                enums.add(data.getAliases());
            }

            for (ChainedSubCommandData subcommand : data.getSubcommands()) {
                if (subCommandData.contains(subcommand)) {
                    continue;
                }

                subCommandData.add(subcommand);
                for (ChainedSubCommandData.Value value : subcommand.getValues()) {
                    if (subCommandValues.contains(value.getFirst())) {
                        subCommandValues.add(value.getFirst());
                    }

                    if (subCommandValues.contains(value.getSecond())) {
                        subCommandValues.add(value.getSecond());
                    }
                }
            }

            for (CommandOverloadData overload : data.getOverloads()) {
                for (CommandParamData parameter : overload.getOverloads()) {
                    CommandEnumData commandEnumData = parameter.getEnumData();
                    if (commandEnumData != null) {
                        if (commandEnumData.isSoft()) {
                            softEnums.add(commandEnumData);
                        } else {
                            enums.add(commandEnumData);
                            int enumIndex = enums.indexOf(commandEnumData);
                            commandEnumData.getValues().forEach((key, constraints) -> {
                                enumValues.add(key);
                                if (!constraints.isEmpty()) {
                                    int valueIndex = enumValues.indexOf(key);
                                    enumConstraints.add(LongObjectPair.of(LongKeys.key(valueIndex, enumIndex), constraints));
                                }
                            });
                        }
                    }

                    String postfix = parameter.getPostfix();
                    if (postfix != null) {
                        postFixes.add(postfix);
                    }
                }
            }
        }

        helper.writeArray(buffer, enumValues, helper::writeString);
        helper.writeArray(buffer, subCommandValues, helper::writeString);
        helper.writeArray(buffer, postFixes, helper::writeString);

        this.writeEnums(buffer, helper, enumValues, enums);
        helper.writeArray(buffer, subCommandData, (buf, value) -> this.writeSubCommand(buffer, helper, subCommandValues, value));

        helper.writeArray(buffer, packet.getCommands(), (buf, command) -> {
            this.writeCommand(buffer, helper, command, enums, softEnums, postFixes, subCommandData);
        });

        helper.writeArray(buffer, softEnums, helper::writeCommandEnum);

        helper.writeArray(buffer, enumConstraints, this::writeEnumConstraint);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableCommandsPacket packet) {
        SequencedHashSet<String> enumValues = new SequencedHashSet<>();
        SequencedHashSet<String> subCommandValues = new SequencedHashSet<>();
        SequencedHashSet<String> postFixes = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> enums = new SequencedHashSet<>();
        SequencedHashSet<ChainedSubCommandData> subCommandData = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> softEnums = new SequencedHashSet<>();
        Set<Consumer<List<CommandEnumData>>> softEnumParameters = new HashSet<>();

        helper.readArray(buffer, enumValues, helper::readString);
        helper.readArray(buffer, subCommandValues, helper::readString);
        helper.readArray(buffer, postFixes, helper::readString);

        this.readEnums(buffer, helper, enumValues, enums);

        helper.readArray(buffer, subCommandData, (buf, hel) -> this.readSubCommand(buf, hel, subCommandValues));

        helper.readArray(buffer, packet.getCommands(), (buf, aHelper) ->
                this.readCommand(buf, aHelper, enums, postFixes, softEnumParameters, subCommandData));

        helper.readArray(buffer, softEnums, buf -> helper.readCommandEnum(buffer, true));

        this.readConstraints(buffer, helper, enums, enumValues);

        softEnumParameters.forEach(consumer -> consumer.accept(softEnums));
    }

    protected void writeCommand(ByteBuf buffer, BedrockCodecHelper helper, CommandData commandData,
                                List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes, List<ChainedSubCommandData> subCommands) {
        helper.writeString(buffer, commandData.getName());
        helper.writeString(buffer, commandData.getDescription());
        this.writeFlags(buffer, commandData.getFlags());
        CommandPermission permission = commandData.getPermission() == null ? CommandPermission.ANY : commandData.getPermission();
        buffer.writeByte(permission.ordinal());

        CommandEnumData aliases = commandData.getAliases();
        buffer.writeIntLE(aliases == null ? -1 : enums.indexOf(aliases));

        helper.writeArray(buffer, commandData.getSubcommands(), (buf, subcommand) -> {
            int index = subCommands.indexOf(subcommand);
            checkArgument(index > -1, "Invalid subcommand index: " + subcommand);
            buf.writeShortLE(index);
        });

        CommandOverloadData[] overloads = commandData.getOverloads();
        VarInts.writeUnsignedInt(buffer, overloads.length);
        for (CommandOverloadData overload : overloads) {
            buffer.writeBoolean(overload.isChaining());
            VarInts.writeUnsignedInt(buffer, overload.getOverloads().length);
            for (CommandParamData param : overload.getOverloads()) {
                this.writeParameter(buffer, helper, param, enums, softEnums, postFixes);
            }
        }
    }

    protected CommandData readCommand(ByteBuf buffer, BedrockCodecHelper helper, List<CommandEnumData> enums,
                                      List<String> postfixes, Set<Consumer<List<CommandEnumData>>> softEnumParameters, List<ChainedSubCommandData> subCommandsList) {
        String name = helper.readString(buffer);
        String description = helper.readString(buffer);
        Set<CommandData.Flag> flags = this.readFlags(buffer);
        CommandPermission permissions = PERMISSIONS[buffer.readUnsignedByte()];
        int aliasIndex = buffer.readIntLE();
        CommandEnumData aliases = aliasIndex == -1 ? null : enums.get(aliasIndex);

        List<ChainedSubCommandData> subcommands = new ObjectArrayList<>();
        helper.readArray(buffer, subcommands, (buf, help) -> {
            int index = buf.readUnsignedShortLE();
            return subCommandsList.get(index);
        });

        CommandOverloadData[] overloads = new CommandOverloadData[VarInts.readUnsignedInt(buffer)];
        for (int i = 0; i < overloads.length; i++) {
            boolean chaining = buffer.readBoolean();
            CommandParamData[] params = new CommandParamData[VarInts.readUnsignedInt(buffer)];
            overloads[i] = new CommandOverloadData(chaining, params);
            for (int i2 = 0; i2 < params.length; i2++) {
                params[i2] = readParameter(buffer, helper, enums, postfixes, softEnumParameters);
            }
        }
        return new CommandData(name, description, flags, permissions, aliases, subcommands, overloads);
    }

    protected void writeSubCommand(ByteBuf buffer, BedrockCodecHelper helper, List<String> values, ChainedSubCommandData data) {
        helper.writeString(buffer, data.getName());
        helper.writeArray(buffer, data.getValues(), (buf, val) -> {
            int first = values.indexOf(val.getFirst());
            checkArgument(first > -1, "Invalid enum value detected: " + val.getFirst());

            int second = values.indexOf(val.getSecond());
            checkArgument(second > -1, "Invalid enum value detected: " + val.getSecond());

            buf.writeShortLE(first);
            buf.writeShortLE(second);
        });
    }

    protected ChainedSubCommandData readSubCommand(ByteBuf buffer, BedrockCodecHelper helper, List<String> values) {
        String name = helper.readString(buffer);
        ChainedSubCommandData data = new ChainedSubCommandData(name);

        helper.readArray(buffer, data.getValues(), buf -> {
            int first = buf.readUnsignedShortLE();
            int second = buf.readUnsignedShortLE();
            return new ChainedSubCommandData.Value(values.get(first), values.get(second));
        });
        return data;
    }
}
