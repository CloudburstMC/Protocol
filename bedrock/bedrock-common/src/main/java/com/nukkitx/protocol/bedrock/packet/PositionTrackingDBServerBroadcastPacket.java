package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PositionTrackingDBServerBroadcastPacket extends BedrockPacket {
    private Action action;
    private int trackingId;
    private CompoundTag tag;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.POSITION_TRACKING_DB_SERVER_BROADCAST;
    }

    public enum Action {
        UPDATE,
        DESTROY,
        NOT_FOUND
    }
}
