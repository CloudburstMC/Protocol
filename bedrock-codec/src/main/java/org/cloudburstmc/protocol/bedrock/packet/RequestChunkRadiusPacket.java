package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RequestChunkRadiusPacket implements BedrockPacket {
    private int radius;
    /**
     * @since v582
     */
    private int maxRadius;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_CHUNK_RADIUS;
    }

    @Override
    public RequestChunkRadiusPacket clone() {
        try {
            return (RequestChunkRadiusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

