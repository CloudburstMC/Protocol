package com.nukkitx.protocol.bedrock.compat;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.compat.packet.LoginSerializerCompat;
import com.nukkitx.protocol.bedrock.compat.packet.PacketHeaderSerializerCompat;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;

public class BedrockCompat {
    /**
     * This is for servers when figuring out the protocol of a client joining.
     */
    public static BedrockPacketCodec COMPAT_CODEC = BedrockPacketCodec.builder()
            .headerSerializer(PacketHeaderSerializerCompat.INSTANCE)
            .registerPacket(LoginPacket.class, LoginSerializerCompat.INSTANCE, 1)
            .addCompatibleVersion(0)
            .build();
}
