package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ScriptCustomEventPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ScriptCustomEventPacket_v291 extends ScriptCustomEventPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, eventName);
        BedrockUtils.writeString(buffer, data);
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        throw new UnsupportedOperationException();
    }
}

