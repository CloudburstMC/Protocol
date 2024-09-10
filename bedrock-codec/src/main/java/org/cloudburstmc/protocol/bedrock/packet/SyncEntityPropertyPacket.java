package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SyncEntityPropertyPacket implements BedrockPacket {
    private NbtMap data;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SYNC_ENTITY_PROPERTY;
    }

    @Override
    public SyncEntityPropertyPacket clone() {
        try {
            return (SyncEntityPropertyPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
