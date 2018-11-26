package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.SetEntityLinkPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SetEntityLinkPacket_v291 extends SetEntityLinkPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeEntityLink(buffer, entityLink);
    }

    @Override
    public void decode(ByteBuf buffer) {
        entityLink = BedrockUtils.readEntityLink(buffer);
    }
}
