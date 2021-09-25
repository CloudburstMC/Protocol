package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerListPacket extends BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;

    public PlayerListPacket addEntry(Entry entry) {
        this.entries.add(entry);
        return this;
    }

    public PlayerListPacket addEntries(Entry... entries) {
        this.entries.addAll(Arrays.asList(entries));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_LIST;
    }

    public enum Action {
        ADD,
        REMOVE
    }

    @ToString
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(doNotUseGetters = true)
    public final static class Entry {
        private final UUID uuid;
        private long entityId;
        private String name;
        private String xuid;
        private String platformChatId;
        private int buildPlatform;
        private SerializedSkin skin;
        private boolean teacher;
        private boolean host;
        private boolean trustedSkin;
    }
}
