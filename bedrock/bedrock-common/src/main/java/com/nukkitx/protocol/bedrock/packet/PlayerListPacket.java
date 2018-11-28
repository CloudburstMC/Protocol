package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
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
    protected final List<Entry> entries = new ArrayList<>();
    protected Type type;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        ADD,
        REMOVE
    }

    @ToString(exclude = {"entityId", "name", "skinData", "capeData", "geometryData", "xuid", "platformChatId"})
    @EqualsAndHashCode(exclude = {"skinData", "capeData", "geometryData"})
    @Data
    public final static class Entry {
        protected final UUID uuid;
        protected long entityId;
        protected String name;
        protected String skinId;
        protected byte[] skinData;
        protected byte[] capeData;
        protected String geometryName;
        protected String geometryData;
        protected String xuid;
        protected String platformChatId;
    }
}
