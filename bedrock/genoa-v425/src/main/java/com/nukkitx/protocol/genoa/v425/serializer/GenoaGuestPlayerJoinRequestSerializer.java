package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaGuestPlayerJoinRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenoaGuestPlayerJoinRequestSerializer implements BedrockPacketSerializer<GenoaGuestPlayerJoinRequestPacket> {
    public static final GenoaGuestPlayerJoinRequestSerializer INSTANCE = new GenoaGuestPlayerJoinRequestSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaGuestPlayerJoinRequestPacket packet) {
        System.out.println(packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaGuestPlayerJoinRequestPacket packet) {
        System.out.println(packet);
    }
}


