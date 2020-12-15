package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetScoreboardIdentityPacket extends BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_SCOREBOARD_IDENTITY;
    }

    public enum Action {
        ADD,
        REMOVE
    }

    @Value
    public static class Entry {
        private final long scoreboardId;
        private final UUID uuid;
    }
}
