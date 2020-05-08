package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.SetEntityLinkPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetEntityLinkSerializer_v363 implements PacketSerializer<SetEntityLinkPacket> {
    public static final SetEntityLinkSerializer_v363 INSTANCE = new SetEntityLinkSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, SetEntityLinkPacket packet) {
        BedrockUtils.writeEntityLink(buffer, packet.getEntityLink());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetEntityLinkPacket packet) {
        packet.setEntityLink(BedrockUtils.readEntityLink(buffer));
    }
}
