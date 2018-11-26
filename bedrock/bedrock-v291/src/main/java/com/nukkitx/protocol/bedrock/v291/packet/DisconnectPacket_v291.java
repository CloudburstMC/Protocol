package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class DisconnectPacket_v291 extends DisconnectPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeBoolean(disconnectScreenHidden);
        if (!disconnectScreenHidden) {
            BedrockUtils.writeString(buffer, kickMessage);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        disconnectScreenHidden = buffer.readBoolean();
        if (!disconnectScreenHidden) {
            kickMessage = BedrockUtils.readString(buffer);
        }
    }
}
