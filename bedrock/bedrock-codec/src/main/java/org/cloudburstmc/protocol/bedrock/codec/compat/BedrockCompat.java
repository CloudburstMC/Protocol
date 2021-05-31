package org.cloudburstmc.protocol.bedrock.codec.compat;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.DisconnectSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.LoginSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.PlayStatusSerializerCompat;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;

public class BedrockCompat {
    /**
     * This is for servers when figuring out the protocol of a client joining.
     */
    public static BedrockCodec COMPAT_CODEC = BedrockCodec.builder()
            .helper(() -> NoopBedrockCodecHelper.INSTANCE)
            .registerPacket(LoginPacket.class, LoginSerializerCompat.INSTANCE, 1)
            .registerPacket(PlayStatusPacket.class, PlayStatusSerializerCompat.INSTANCE, 2)
            .registerPacket(DisconnectPacket.class, DisconnectSerializerCompat.INSTANCE, 5)
            .protocolVersion(0)
            .minecraftVersion("0.0.0")
            .build();
}
