package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true, exclude = "definitions")
public class CompressedBiomeDefinitionListPacket implements BedrockPacket {
    private NbtMap definitions;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.COMPRESSED_BIOME_DEFINITIONS_LIST;
    }

    @Override
    public CompressedBiomeDefinitionListPacket clone() {
        try {
            return (CompressedBiomeDefinitionListPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

