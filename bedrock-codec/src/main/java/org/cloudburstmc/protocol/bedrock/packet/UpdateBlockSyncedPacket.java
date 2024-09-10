package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.data.BlockSyncType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
public class UpdateBlockSyncedPacket extends UpdateBlockPacket {
    private long runtimeEntityId;
    private BlockSyncType entityBlockSyncType;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_BLOCK_SYNCED;
    }

    public String toString() {
        return "UpdateBlockSyncedPacket(runtimeEntityId=" + this.runtimeEntityId +
                ", entityBlockSyncType=" + this.entityBlockSyncType +
                ", flags=" + this.flags +
                ", blockPosition=" + this.blockPosition +
                ", definition=" + this.definition +
                ", dataLayer=" + this.dataLayer +
                ")";
    }

    @Override
    public UpdateBlockSyncedPacket clone() {
        return (UpdateBlockSyncedPacket) super.clone();
    }
}
