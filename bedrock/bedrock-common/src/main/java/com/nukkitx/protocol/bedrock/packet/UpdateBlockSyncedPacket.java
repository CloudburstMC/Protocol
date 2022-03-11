package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.BlockSyncType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class UpdateBlockSyncedPacket extends UpdateBlockPacket {
    private long runtimeEntityId;
    private BlockSyncType entityBlockSyncType;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
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
                ", runtimeId=" + this.runtimeId +
                ", dataLayer=" + this.dataLayer +
                ")";
    }
}
