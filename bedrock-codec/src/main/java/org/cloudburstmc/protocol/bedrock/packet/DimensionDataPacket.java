package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sends a list of the data-driven dimensions to the client.
 * This packet is sent before the {@link StartGamePacket} in the login sequence.
 *
 * <b>Note:</b> The client only supports sending the <code>minecraft:overworld</code> dimension as of 1.18.30
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DimensionDataPacket implements BedrockPacket {
    private final List<DimensionDefinition> definitions = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DIMENSION_DATA;
    }

    @Override
    public DimensionDataPacket clone() {
        try {
            return (DimensionDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

