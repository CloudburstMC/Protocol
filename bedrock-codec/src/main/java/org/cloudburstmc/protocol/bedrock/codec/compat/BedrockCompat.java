package org.cloudburstmc.protocol.bedrock.codec.compat;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.LoginSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.PlayStatusSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.RequestNetworkSettingsSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.DisconnectSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v622.Bedrock_v622;
import org.cloudburstmc.protocol.bedrock.codec.v622.serializer.DisconnectSerializer_v622;
import org.cloudburstmc.protocol.bedrock.codec.v712.Bedrock_v712;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.DisconnectSerializer_v712;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket;

public class BedrockCompat {
    /**
     * This is for servers when figuring out the protocol of a client joining.
     * Certain protocols that are older will need to use {@link #disconnectCompat(int)} before disconnecting,
     * in order for the client to see a proper disconnect message.
     */
    public static BedrockCodec CODEC = BedrockCodec.builder()
            .helper(() -> NoopBedrockCodecHelper.INSTANCE)
            .registerPacket(LoginPacket::new, LoginSerializerCompat.INSTANCE, 1, PacketRecipient.SERVER)
            .registerPacket(PlayStatusPacket::new, PlayStatusSerializerCompat.INSTANCE, 2, PacketRecipient.CLIENT)
            .registerPacket(DisconnectPacket::new, DisconnectSerializer_v712.INSTANCE, 5, PacketRecipient.BOTH)
            .registerPacket(RequestNetworkSettingsPacket::new, RequestNetworkSettingsSerializerCompat.INSTANCE, 193, PacketRecipient.SERVER)
            .protocolVersion(0)
            .minecraftVersion("0.0.0")
            .build();

    /**
     * A legacy version of the compat codec which does not use DisconnectFailReason in DisconnectPacket.
     * Use this for protocols lower than v622
     */
    public static BedrockCodec CODEC_v291 = CODEC.toBuilder()
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v291.INSTANCE)
            .build();

    /**
     * A legacy version of the compat codec which does not use filteredMessage in DisconnectPacket.
     * Use this for protocols equal to or greater than v622, but less than v712
     */
    public static BedrockCodec CODEC_v622 = CODEC.toBuilder()
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v622.INSTANCE)
            .build();

    /**
     * Certain older protocols have outdated {@link DisconnectPacket} formats.
     * Using the codec provided by this method before disconnecting a client will always
     * ensure they receive a well-formed DisconnectPacket.
     * <p>
     * Note that this compat codec should only be used if disconnecting the client upon receiving
     * its protocol version. Otherwise, use a proper codec that targets the specific protocol.
     *
     * @param protocolVersion the client's protocol version
     * @return a compat codec suitable for disconnecting
     */
    public static BedrockCodec disconnectCompat(int protocolVersion) {
        if (protocolVersion < Bedrock_v622.CODEC.getProtocolVersion()) {
            return CODEC_v291;
        } else if (protocolVersion < Bedrock_v712.CODEC.getProtocolVersion()) {
            return CODEC_v622;
        }
        return CODEC; // v712 or above
    }
}
