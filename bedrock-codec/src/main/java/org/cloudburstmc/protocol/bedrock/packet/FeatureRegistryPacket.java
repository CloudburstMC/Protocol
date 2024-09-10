package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.definitions.FeatureDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * World generation features used for client-side chunk generation.
 *
 * @since 1.19.20
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class FeatureRegistryPacket implements BedrockPacket {
    private final List<FeatureDefinition> features = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.FEATURE_REGISTRY;
    }

    @Override
    public FeatureRegistryPacket clone() {
        try {
            return (FeatureRegistryPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

