package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2i;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NetworkChunkPublisherUpdatePacket implements BedrockPacket {
    private Vector3i position;
    private int radius;
    /**
     * Lets the client know which chunks have been saved, and need
     * requesting whilst client chunk generation is enabled.
     *
     * @since 1.19.20
     */
    private final List<Vector2i> savedChunks = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NETWORK_CHUNK_PUBLISHER_UPDATE;
    }

    @Override
    public NetworkChunkPublisherUpdatePacket clone() {
        try {
            return (NetworkChunkPublisherUpdatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

