package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.CommandData;
import com.nukkitx.protocol.bedrock.data.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.CommandParamData;
import com.nukkitx.protocol.bedrock.data.CommandParamType;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import static com.nukkitx.protocol.bedrock.data.CommandParamData.Type.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableCommandsSerializer_v332 implements PacketSerializer<AvailableCommandsPacket> {
    public static final AvailableCommandsSerializer_v332 INSTANCE = new AvailableCommandsSerializer_v332();
    private static final ObjIntConsumer<ByteBuf> WRITE_BYTE = ByteBuf::writeByte;
    private static final ObjIntConsumer<ByteBuf> WRITE_SHORT = ByteBuf::writeShortLE;
    private static final ObjIntConsumer<ByteBuf> WRITE_INT = ByteBuf::writeIntLE;
    private static final ToIntFunction<ByteBuf> READ_BYTE = ByteBuf::readByte;
    private static final ToIntFunction<ByteBuf> READ_SHORT = ByteBuf::readShortLE;
    private static final ToIntFunction<ByteBuf> READ_INT = ByteBuf::readIntLE;
    private static final TIntHashBiMap<CommandParamData.Type> PARAM_TYPES = new TIntHashBiMap<>();

    static {
        PARAM_TYPES.put(1, INT);
        PARAM_TYPES.put(2, FLOAT);
        PARAM_TYPES.put(3, VALUE);
        PARAM_TYPES.put(4, WILDCARD_INT);
        PARAM_TYPES.put(5, OPERATOR);
        PARAM_TYPES.put(6, TARGET);
        PARAM_TYPES.put(7, WILDCARD_TARGET);
        PARAM_TYPES.put(15, FILE_PATH);
        PARAM_TYPES.put(19, INT_RANGE);
        PARAM_TYPES.put(28, STRING);
        PARAM_TYPES.put(30, POSITION);
        PARAM_TYPES.put(33, MESSAGE);
        PARAM_TYPES.put(35, TEXT);
        PARAM_TYPES.put(38, JSON);
        PARAM_TYPES.put(45, COMMAND);
    }

    @Override
    public void serialize(ByteBuf buffer, AvailableCommandsPacket packet) {
        Set<String> enumValuesSet = new HashSet<>();
        Set<String> postfixSet = new HashSet<>();
        Set<CommandEnumData> enumsSet = new HashSet<>();
        Set<CommandEnumData> softEnumsSet = new HashSet<>();

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

        List<String> enumValues = new ArrayList<>(enumValuesSet);
        List<String> postFixes = new ArrayList<>(postfixSet);
        List<CommandEnumData> enums = new ArrayList<>(enumsSet);
        List<CommandEnumData> softEnums = new ArrayList<>(softEnumsSet);

        // Determine width of enum index
        ObjIntConsumer<ByteBuf> indexWriter;
        int valuesSize = enumValues.size();
        if (valuesSize < 0x100) {
            indexWriter = WRITE_BYTE;
        } else if (valuesSize < 0x10000) {
            indexWriter = WRITE_SHORT;
        } else {
            indexWriter = WRITE_INT;
        }

        BedrockUtils.writeArray(buffer, enumValues, BedrockUtils::writeString);
        BedrockUtils.writeArray(buffer, postFixes, BedrockUtils::writeString);

        // Write enums
        BedrockUtils.writeArray(buffer, enums, (buf, commandEnum) -> {
            BedrockUtils.writeString(buf, commandEnum.getName());

            VarInts.writeUnsignedInt(buffer, commandEnum.getValues().length);
            for (String value : commandEnum.getValues()) {
                int index = enumValues.indexOf(value);
                Preconditions.checkArgument(index > -1, "Invalid enum value detected");
                indexWriter.accept(buf, index);
            }
        });

        // Write command data
        BedrockUtils.writeArray(buffer, packet.getCommands(), (buf, commandData) -> {
            BedrockUtils.writeString(buf, commandData.getName());
            BedrockUtils.writeString(buf, commandData.getDescription());
            byte flags = 0;
            for (CommandData.Flag flag : commandData.getFlags()) {
                flags |= 1 << flag.ordinal();
            }
            buf.writeByte(flags);
            buf.writeByte(commandData.getPermission());

            CommandEnumData aliases = commandData.getAliases();
            buf.writeIntLE(enums.indexOf(aliases));

            CommandParamData[][] overloads = commandData.getOverloads();
            VarInts.writeUnsignedInt(buf, overloads.length);
            for (CommandParamData[] overload : overloads) {
                VarInts.writeUnsignedInt(buf, overload.length);
                for (CommandParamData param : overload) {
                    BedrockUtils.writeString(buf, param.getName());

                    int index = 0;
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
                        index = PARAM_TYPES.get(param.getType());
                    } else {
                        throw new IllegalStateException("No param type specified");
                    }

                    CommandParamType type = new CommandParamType(index, enumData, softEnum, postfix);

                    buf.writeIntLE(type.serialize());
                    buf.writeBoolean(param.isOptional());
                }
            }
        });

        BedrockUtils.writeArray(buffer, softEnums, BedrockUtils::writeCommandEnumData);
    }

    @Override
    public void deserialize(ByteBuf buffer, AvailableCommandsPacket packet) {
        List<String> enumValues = new ArrayList<>();
        List<String> postFixes = new ArrayList<>();
        List<CommandEnumData> enums = new ArrayList<>();
        List<CommandData.Builder> commands = new ArrayList<>();
        List<CommandEnumData> softEnums = new ArrayList<>();

        BedrockUtils.readArray(buffer, enumValues, BedrockUtils::readString);
        BedrockUtils.readArray(buffer, postFixes, BedrockUtils::readString);

        // Determine width of enum index
        ToIntFunction<ByteBuf> indexReader;
        int valuesSize = enumValues.size();
        if (valuesSize < 0x100) {
            indexReader = READ_BYTE;
        } else if (valuesSize < 0x10000) {
            indexReader = READ_SHORT;
        } else {
            indexReader = READ_INT;
        }

        BedrockUtils.readArray(buffer, enums, buf -> {
            String name = BedrockUtils.readString(buf);

            int length = VarInts.readUnsignedInt(buffer);
            String[] values = new String[length];
            for (int i = 0; i < length; i++) {
                values[i] = enumValues.get(indexReader.applyAsInt(buf));
            }
            return new CommandEnumData(name, values, false);
        });

        BedrockUtils.readArray(buffer, commands, buf -> {
            String name = BedrockUtils.readString(buf);
            String description = BedrockUtils.readString(buf);
            byte flags = buf.readByte();
            byte permissions = buf.readByte();
            int aliasesIndex = buf.readIntLE();

            CommandParamData.Builder[][] overloads = new CommandParamData.Builder[VarInts.readUnsignedInt(buf)][];
            for (int i = 0; i < overloads.length; i++) {
                overloads[i] = new CommandParamData.Builder[VarInts.readUnsignedInt(buf)];
                for (int i2 = 0; i2 < overloads[i].length; i2++) {
                    String parameterName = BedrockUtils.readString(buf);

                    CommandParamType type = CommandParamType.deserialize(buf.readIntLE());

                    boolean optional = buf.readBoolean();

                    overloads[i][i2] = new CommandParamData.Builder(parameterName, type, optional);
                }
            }
            return new CommandData.Builder(name, description, flags, permissions, aliasesIndex, overloads);
        });

        BedrockUtils.readArray(buffer, softEnums, buf -> BedrockUtils.readCommandEnumData(buffer, true));

        // Generate command data

        for (CommandData.Builder command : commands) {
            byte flags = command.getFlags();
            List<CommandData.Flag> flagList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                if (((flags >>> i) & 0xf) != 0) {
                    flagList.add(CommandData.Flag.values()[i]);
                }
            }
            int aliasesIndex = command.getAliases();
            CommandEnumData aliases = aliasesIndex == -1 ? null : enums.get(aliasesIndex);

            CommandParamData.Builder[][] overloadBuilders = command.getOverloads();
            CommandParamData[][] overloads = new CommandParamData[overloadBuilders.length][];
            for (int i = 0; i < overloadBuilders.length; i++) {
                overloads[i] = new CommandParamData[overloadBuilders[i].length];
                for (int i2 = 0; i2 < overloadBuilders[i].length; i2++) {
                    String name = overloadBuilders[i][i2].getName();
                    CommandParamType type = overloadBuilders[i][i2].getType();
                    boolean optional = overloadBuilders[i][i2].isOptional();

                    String postfix = null;
                    CommandEnumData enumData = null;
                    CommandParamData.Type paramType = null;
                    if (type.isPostfix()) {
                        postfix = postFixes.get(type.getValue());
                    } else {
                        if (type.isCommandEnum()) {
                            enumData = enums.get(type.getValue());
                        } else if (type.isSoftEnum()) {
                            enumData = softEnums.get(type.getValue());
                        } else {
                            paramType = PARAM_TYPES.get(type.getValue());
                        }
                    }
                    overloads[i][i2] = new CommandParamData(name, optional, enumData, paramType, postfix, Collections.emptyList());
                }
            }

            packet.getCommands().add(new CommandData(command.getName(), command.getDescription(),
                    flagList, command.getPermission(), aliases, overloads));
        }
    }
}