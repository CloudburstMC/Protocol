package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Initial packet sent in the login sequence by the client. The server is expected to respond to
 * this packet with the {@link NetworkSettingsPacket} and apply the compression settings that
 * are defined in the packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class RequestNetworkSettingsPacket implements BedrockPacket {
    private int protocolVersion;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_NETWORK_SETTINGS;
    }

    @Override
    public RequestNetworkSettingsPacket clone() {
        try {
            return (RequestNetworkSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
