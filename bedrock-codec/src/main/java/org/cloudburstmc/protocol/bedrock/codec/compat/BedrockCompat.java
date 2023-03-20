package org.cloudburstmc.protocol.bedrock.codec.compat;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.DisconnectSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.LoginSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.PlayStatusSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.RequestNetworkSettingsSerializerCompat;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket;

public class BedrockCompat {
    /**
     * This is for servers when figuring out the protocol of a client joining.
     */
    public static BedrockCodec CODEC = BedrockCodec.builder()
            .helper(() -> NoopBedrockCodecHelper.INSTANCE)
            .registerPacket(LoginPacket::new, LoginSerializerCompat.INSTANCE, 1)
            .registerPacket(PlayStatusPacket::new, PlayStatusSerializerCompat.INSTANCE, 2)
            .registerPacket(DisconnectPacket::new, DisconnectSerializerCompat.INSTANCE, 5)
            .registerPacket(RequestNetworkSettingsPacket::new, RequestNetworkSettingsSerializerCompat.INSTANCE, 193)
            .protocolVersion(0)
            .minecraftVersion("0.0.0")
            .build();
}
