package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.command.*;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableCommandsSerializer_v291 implements BedrockPacketSerializer<AvailableCommandsPacket> {
    public static final AvailableCommandsSerializer_v291 INSTANCE = new AvailableCommandsSerializer_v291();

    protected static final ObjIntConsumer<ByteBuf> WRITE_BYTE = ByteBuf::writeByte;
    protected static final ObjIntConsumer<ByteBuf> WRITE_SHORT = ByteBuf::writeShortLE;
    protected static final ObjIntConsumer<ByteBuf> WRITE_INT = ByteBuf::writeIntLE;
    protected static final ToIntFunction<ByteBuf> READ_BYTE = ByteBuf::readUnsignedByte;
    protected static final ToIntFunction<ByteBuf> READ_SHORT = ByteBuf::readUnsignedShortLE;
    protected static final ToIntFunction<ByteBuf> READ_INT = ByteBuf::readIntLE;

    protected static final CommandData.Flag[] FLAGS = CommandData.Flag.values();
    protected static final CommandParamOption[] OPTIONS = CommandParamOption.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableCommandsPacket packet) {
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
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableCommandsPacket packet) {
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
            int flags = command.getFlags();
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
                    } else if (type.isCommandEnum()) {
                        enumData = enums.get(type.getValue());
                    } else if (type.isSoftEnum()) {
                        enumData = softEnums.get(type.getValue());
                    } else {
                        commandParam = helper.getCommandParam(type.getValue());
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
    }

    protected void writeEnums(ByteBuf buffer, BedrockPacketHelper helper, List<String> values, List<CommandEnumData> enums) {
        // Determine width of enum index
        ObjIntConsumer<ByteBuf> indexWriter;
        int valuesSize = values.size();
        if (valuesSize < 0x100) {
            indexWriter = WRITE_BYTE;
        } else if (valuesSize < 0x10000) {
            indexWriter = WRITE_SHORT;
        } else {
            indexWriter = WRITE_INT;
        }

        // Write enums
        helper.writeArray(buffer, enums, (buf, commandEnum) -> {
            helper.writeString(buf, commandEnum.getName());

            VarInts.writeUnsignedInt(buffer, commandEnum.getValues().length);
            for (String value : commandEnum.getValues()) {
                int index = values.indexOf(value);
                Preconditions.checkArgument(index > -1, "Invalid enum value detected: " + value);
                indexWriter.accept(buf, index);
            }
        });
    }

    protected void readEnums(ByteBuf buffer, BedrockPacketHelper helper, List<String> values, List<CommandEnumData> enums) {
        // Determine width of enum index
        ToIntFunction<ByteBuf> indexReader;
        int valuesSize = values.size();
        if (valuesSize < 0x100) {
            indexReader = READ_BYTE;
        } else if (valuesSize < 0x10000) {
            indexReader = READ_SHORT;
        } else {
            indexReader = READ_INT;
        }

        // Construct enums with offsets
        helper.readArray(buffer, enums, buf -> {
            String name = helper.readString(buf);

            int length = VarInts.readUnsignedInt(buffer);
            String[] enumValues = new String[length];
            for (int i = 0; i < length; i++) {
                enumValues[i] = values.get(indexReader.applyAsInt(buf));
            }
            return new CommandEnumData(name, enumValues, false);
        });
    }

    protected void writeCommand(ByteBuf buffer, BedrockPacketHelper helper, CommandData commandData,
                                List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
        helper.writeString(buffer, commandData.getName());
        helper.writeString(buffer, commandData.getDescription());
        byte flags = 0;
        for (CommandData.Flag flag : commandData.getFlags()) {
            flags |= 1 << flag.ordinal();
        }
        buffer.writeByte(flags);
        buffer.writeByte(commandData.getPermission());

        CommandEnumData aliases = commandData.getAliases();
        buffer.writeIntLE(enums.indexOf(aliases));

        CommandParamData[][] overloads = commandData.getOverloads();
        VarInts.writeUnsignedInt(buffer, overloads.length);
        for (CommandParamData[] overload : overloads) {
            VarInts.writeUnsignedInt(buffer, overload.length);
            for (CommandParamData param : overload) {
                this.writeParameter(buffer, helper, param, enums, softEnums, postFixes);
            }
        }
    }

    protected CommandData.Builder readCommand(ByteBuf buffer, BedrockPacketHelper helper) {
        String name = helper.readString(buffer);
        String description = helper.readString(buffer);
        byte flags = buffer.readByte();
        byte permissions = buffer.readByte();
        int aliasesIndex = buffer.readIntLE();

        CommandParamData.Builder[][] overloads = new CommandParamData.Builder[VarInts.readUnsignedInt(buffer)][];
        for (int i = 0; i < overloads.length; i++) {
            overloads[i] = new CommandParamData.Builder[VarInts.readUnsignedInt(buffer)];
            for (int i2 = 0; i2 < overloads[i].length; i2++) {
                overloads[i][i2] = readParameter(buffer, helper);
            }
        }
        return new CommandData.Builder(name, description, flags, permissions, aliasesIndex, overloads);
    }

    protected void writeParameter(ByteBuf buffer, BedrockPacketHelper helper, CommandParamData param,
                                  List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
        helper.writeString(buffer, param.getName());

        int index;
        boolean postfix = false;
        boolean enumData = false;
        boolean softEnum = false;
        if (param.getPostfix() != null) {
            postfix = true;
            index = postFixes.indexOf(param.getPostfix());
        } else if (param.getEnumData() != null) {
            if (param.getEnumData().isSoft()) {
                softEnum = true;
                index = softEnums.indexOf(param.getEnumData());
            } else {
                enumData = true;
                index = enums.indexOf(param.getEnumData());
            }
        } else if (param.getType() != null) {
            index = param.getType().getValue(helper);
        } else {
            throw new IllegalStateException("No param type specified: " + param);
        }

        CommandSymbolData type = new CommandSymbolData(index, enumData, softEnum, postfix);

        buffer.writeIntLE(type.serialize());
        buffer.writeBoolean(param.isOptional());
    }

    protected CommandParamData.Builder readParameter(ByteBuf buffer, BedrockPacketHelper helper) {
        return new CommandParamData.Builder(
                helper.readString(buffer),
                CommandSymbolData.deserialize(buffer.readIntLE()),
                buffer.readBoolean(),
                (byte) 0
        );
    }
}