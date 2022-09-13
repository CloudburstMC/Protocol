package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PacketCompressionAlgorithm;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Initial packet sent in the login sequence by the client. The server is expected to respond to
 * this packet with the {@link NetworkSettingsPacket} and apply the compression settings that
 * are defined in the packet using
 * {@link com.nukkitx.protocol.bedrock.BedrockSession#setCompression(PacketCompressionAlgorithm)}
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class RequestNetworkSettingsPacket extends BedrockPacket {
    private int protocolVersion;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_NETWORK_SETTINGS;
    }
}
