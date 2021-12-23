package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamData;
import com.nukkitx.protocol.bedrock.v388.serializer.AvailableCommandsSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableCommandsSerializer_v448 extends AvailableCommandsSerializer_v388 {

    public static final AvailableCommandsSerializer_v448 INSTANCE = new AvailableCommandsSerializer_v448();

    @Override
    protected void writeCommand(ByteBuf buffer, BedrockPacketHelper helper, CommandData commandData,
                                List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
        helper.writeString(buffer, commandData.getName());
        helper.writeString(buffer, commandData.getDescription());
        int flags = 0;
        for (CommandData.Flag flag : commandData.getFlags()) {
            flags |= 1 << flag.ordinal();
        }
        buffer.writeShortLE(flags);
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

    @Override
    protected CommandData.Builder readCommand(ByteBuf buffer, BedrockPacketHelper helper) {
        String name = helper.readString(buffer);
        String description = helper.readString(buffer);
        int flags = buffer.readUnsignedShortLE();
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
}
