package org.cloudburstmc.protocol.bedrock.codec.compat;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.DisconnectSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.LoginSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.PlayStatusSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.RequestNetworkSettingsSerializerCompat;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
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
            .registerPacket(LoginPacket::new, LoginSerializerCompat.INSTANCE, 1, PacketRecipient.SERVER)
            .registerPacket(PlayStatusPacket::new, PlayStatusSerializerCompat.INSTANCE, 2, PacketRecipient.CLIENT)
            .registerPacket(DisconnectPacket::new, new DisconnectSerializerCompat(true), 5, PacketRecipient.BOTH)
            .registerPacket(RequestNetworkSettingsPacket::new, RequestNetworkSettingsSerializerCompat.INSTANCE, 193, PacketRecipient.SERVER)
            .protocolVersion(0)
            .minecraftVersion("0.0.0")
            .build();

    /**
     * This is legacy version of the compat codec which does not use DisconnectFailReason in DisconnectPacket.
     * Use this for servers that do not support Minecraft: Bedrock Edition 1.20.40 and above.
     */
    public static BedrockCodec CODEC_LEGACY = CODEC.toBuilder()
            .updateSerializer(DisconnectPacket.class, new DisconnectSerializerCompat(false))
            .build();
}
