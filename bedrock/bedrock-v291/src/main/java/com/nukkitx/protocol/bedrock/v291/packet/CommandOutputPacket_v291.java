package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CommandOutputPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class CommandOutputPacket_v291 extends CommandOutputPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeCommandOriginData(buffer, commandOriginData);
        buffer.writeByte(outputType);
        VarInts.writeUnsignedInt(buffer, successCount);

        VarInts.writeUnsignedInt(buffer, messages.size());
        BedrockUtils.writeArray(buffer, messages, BedrockUtils::writeCommandOutputMessage);

        if (outputType == 4) {
            BedrockUtils.writeString(buffer, data);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        commandOriginData = BedrockUtils.readCommandOriginData(buffer);
        outputType = buffer.readUnsignedByte();
        successCount = VarInts.readUnsignedInt(buffer);

        BedrockUtils.readArray(buffer, messages, BedrockUtils::readCommandOutputMessage);

        if (outputType == 4) {
            data = BedrockUtils.readString(buffer);
        }
    }
}
