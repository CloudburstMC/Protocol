package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ExplodePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ExplodePacket_v291 extends ExplodePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeVector3f(buffer, position);
        buffer.writeFloatLE(radius);

        BedrockUtils.writeArray(buffer, records, BedrockUtils::writeVector3i);
    }

    @Override
    public void decode(ByteBuf buffer) {
        position = BedrockUtils.readVector3f(buffer);
        radius = buffer.readFloatLE();

        BedrockUtils.readArray(buffer, records, BedrockUtils::readVector3i);
    }
}
