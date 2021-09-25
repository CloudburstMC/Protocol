package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetScoreboardIdentityPacket extends BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;

    public SetScoreboardIdentityPacket addEntry(Entry entry) {
        this.entries.add(entry);
        return this;
    }

    public SetScoreboardIdentityPacket addEntries(Entry... entries) {
        this.entries.addAll(Arrays.asList(entries));
        return this;
    }

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
