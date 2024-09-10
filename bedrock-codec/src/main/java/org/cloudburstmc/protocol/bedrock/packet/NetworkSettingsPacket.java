package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.annotation.Incompressible;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@Incompressible
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NetworkSettingsPacket implements BedrockPacket {
    /**
     * The smallest amount of bytes that should be compressed by the client. 0-65535
     */
    private int compressionThreshold;
    /**
     * Set the compression type to be used on the connection.
     *
     * @since v551
     */
    private PacketCompressionAlgorithm compressionAlgorithm;
    /**
     * Enable client throttling of players out of the threshold. Players out of the threshold will not be ticked on the
     * client, reducing the performance hit on lower end devices when in densely populated areas.
     *
     * @since v554
     */
    private boolean clientThrottleEnabled;
    /**
     * @since v554
     */
    private int clientThrottleThreshold;
    /**
     * @since v554
     */
    private float clientThrottleScalar;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NETWORK_SETTINGS;
    }

    @Override
    public NetworkSettingsPacket clone() {
        try {
            return (NetworkSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

