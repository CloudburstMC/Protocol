package com.nukkitx.protocol.bedrock.compat;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.compat.packet.DisconnectPacketCompat;
import com.nukkitx.protocol.bedrock.compat.packet.LoginPacketCompat;
import com.nukkitx.protocol.bedrock.compat.packet.PacketHeaderCompat;
import com.nukkitx.protocol.bedrock.compat.packet.PlayStatusPacketCompat;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;

public class CompatUtils {
    public static BedrockPacketCodec COMPAT_CODEC = BedrockPacketCodec.builder()
            .packetHeader(PacketHeaderCompat::new)
            .registerPacket(LoginPacket.class, LoginPacketCompat::new, 1)
            .registerPacket(PlayStatusPacket.class, PlayStatusPacketCompat::new, 2)
            .registerPacket(DisconnectPacket.class, DisconnectPacketCompat::new, 5)
            .build();
}
