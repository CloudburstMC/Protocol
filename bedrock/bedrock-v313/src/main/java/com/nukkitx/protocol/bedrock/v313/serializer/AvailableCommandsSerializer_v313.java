package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.CommandData;
import com.nukkitx.protocol.bedrock.data.CommandEnum;
import com.nukkitx.protocol.bedrock.data.CommandParameter;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableCommandsSerializer_v313 implements PacketSerializer<AvailableCommandsPacket> {
    public static final AvailableCommandsSerializer_v313 INSTANCE = new AvailableCommandsSerializer_v313();
    private static final ObjIntConsumer<ByteBuf> WRITE_BYTE = ByteBuf::writeByte;
    private static final ObjIntConsumer<ByteBuf> WRITE_SHORT = ByteBuf::writeShortLE;
    private static final ObjIntConsumer<ByteBuf> WRITE_INT = ByteBuf::writeIntLE;
    private static final ToIntFunction<ByteBuf> READ_BYTE = ByteBuf::readByte;
    private static final ToIntFunction<ByteBuf> READ_SHORT = ByteBuf::readShortLE;
    private static final ToIntFunction<ByteBuf> READ_INT = ByteBuf::readIntLE;

    private static final int ARG_FLAG_VALID = 0x100000;
    private static final int ARG_FLAG_ENUM = 0x200000;
    private static final int ARG_FLAG_POSTFIX = 0x1000000;

    @Override
    public void serialize(ByteBuf buffer, AvailableCommandsPacket packet) {
        Set<String> enumValuesSet = new HashSet<>();
        Set<String> postfixSet = new HashSet<>();
        Set<CommandEnum> enumsSet = new HashSet<>();

        // Get all enum values
        for (CommandData data : packet.getCommands()) {
            if (data.getAliases() != null) {
                Collections.addAll(enumValuesSet, data.getAliases().getValues());
                enumsSet.add(data.getAliases());
            }

            for (CommandParameter[] overload : data.getOverloads()) {
                for (CommandParameter parameter : overload) {
                    CommandEnum commandEnum = parameter.getCommandEnum();
                    if (commandEnum != null) {
                        Collections.addAll(enumValuesSet, commandEnum.getValues());
                        enumsSet.add(commandEnum);
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
        List<CommandEnum> enums = new ArrayList<>(enumsSet);

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

            CommandEnum aliases = commandData.getAliases();
            buf.writeIntLE(enums.indexOf(aliases));

            CommandParameter[][] overloads = commandData.getOverloads();
            VarInts.writeUnsignedInt(buf, overloads.length);
            for (CommandParameter[] overload : overloads) {
                VarInts.writeUnsignedInt(buf, overload.length);
                for (CommandParameter parameter : overload) {
                    BedrockUtils.writeString(buf, parameter.getName());

                    int type;
                    if (parameter.getCommandEnum() != null) {
                        type = ARG_FLAG_ENUM | ARG_FLAG_VALID | enums.indexOf(parameter.getCommandEnum());
                    } else if (parameter.getPostfix() != null) {
                        int index = postFixes.indexOf(parameter.getPostfix());
                        Preconditions.checkArgument(index > -1, "Invalid postfix '%s' provided", parameter.getPostfix());
                        type = ARG_FLAG_POSTFIX | index;
                    } else {
                        type = parameter.getType();
                    }

                    buf.writeIntLE(type);
                    buf.writeBoolean(parameter.isOptional());
                }
            }
        });

        BedrockUtils.writeArray(buffer, packet.getSoftEnums(), (buf, commandEnum) -> {
            BedrockUtils.writeString(buf, commandEnum.getName());

            VarInts.writeUnsignedInt(buf, commandEnum.getValues().length);
            for (String value : commandEnum.getValues()) {
                BedrockUtils.writeString(buf, value);
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, AvailableCommandsPacket packet) {
        List<String> enumValues = new ArrayList<>();
        List<String> postFixes = new ArrayList<>();
        List<CommandEnum> enums = new ArrayList<>();

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
            return new CommandEnum(name, values);
        });

        BedrockUtils.readArray(buffer, packet.getCommands(), buf -> {
            String name = BedrockUtils.readString(buf);
            String description = BedrockUtils.readString(buf);

            byte flags = buf.readByte();
            List<CommandData.Flag> flagList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                if (((flags >>> i) & 0xf) != 0) {
                    flagList.add(CommandData.Flag.values()[i]);
                }
            }

            byte permissions = buf.readByte();

            CommandEnum aliases = null;
            int aliasesIndex = buf.readIntLE();
            if (aliasesIndex > -1) {
                aliases = enums.get(aliasesIndex);
            }

            CommandParameter[][] overloads = new CommandParameter[VarInts.readUnsignedInt(buf)][];
            for (int i = 0; i < overloads.length; i++) {
                overloads[i] = new CommandParameter[VarInts.readUnsignedInt(buf)];
                for (int i2 = 0; i2 < overloads[i].length; i2++) {
                    String parameterName = BedrockUtils.readString(buf);

                    int type = buf.readIntLE();
                    CommandEnum commandEnum = null;
                    String postfix = null;
                    if ((type & ARG_FLAG_ENUM) != 0) {
                        commandEnum = enums.get(type & 0xffff);
                    } else if ((type & ARG_FLAG_POSTFIX) != 0) {
                        postfix = postFixes.get(type & 0xffff);
                    } else if ((type & ARG_FLAG_VALID) == 0) {
                        throw new IllegalStateException("Invalid parameter " + Integer.toHexString(type) + " received");
                    }

                    boolean optional = buf.readBoolean();

                    overloads[i][i2] = new CommandParameter(parameterName, type, optional, commandEnum, postfix);
                }
            }
            return new CommandData(name, description, flagList.toArray(new CommandData.Flag[0]), permissions, aliases, overloads);
        });

        BedrockUtils.readArray(buffer, packet.getSoftEnums(), buf -> {
            String name = BedrockUtils.readString(buf);

            String[] values = new String[VarInts.readUnsignedInt(buf)];
            for (int i = 0; i < values.length; i++) {
                values[i] = BedrockUtils.readString(buf);
            }
            return new CommandEnum(name, values);
        });
    }
}
