package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.SerializedSkin;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerListPacket extends BedrockPacket {
    private final List<Entry> entries = new ArrayList<>();
    private Type type;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        ADD,
        REMOVE
    }

    @ToString
    @Data
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
    }
}
