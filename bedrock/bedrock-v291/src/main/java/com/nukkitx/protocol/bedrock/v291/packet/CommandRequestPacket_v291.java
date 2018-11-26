package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class CommandRequestPacket_v291 extends CommandRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, command);
        BedrockUtils.writeCommandOriginData(buffer, commandOriginData);
        buffer.writeBoolean(internal);
    }

    @Override
    public void decode(ByteBuf buffer) {
        command = BedrockUtils.readString(buffer);
        commandOriginData = BedrockUtils.readCommandOriginData(buffer);
        internal = buffer.readBoolean();
    }
}
