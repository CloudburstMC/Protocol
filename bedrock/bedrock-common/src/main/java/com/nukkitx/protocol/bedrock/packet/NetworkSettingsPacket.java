package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.annotation.Incompressible;
import com.nukkitx.protocol.bedrock.data.PacketCompressionAlgorithm;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Incompressible
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class NetworkSettingsPacket extends BedrockPacket {
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
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NETWORK_SETTINGS;
    }
}
