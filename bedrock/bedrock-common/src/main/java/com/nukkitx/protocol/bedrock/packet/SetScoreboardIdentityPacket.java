package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class SetScoreboardIdentityPacket extends BedrockPacket {
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

    @Value
    public static class Entry {
        protected final long scoreboardId;
        protected final UUID uuid;
    }
}
