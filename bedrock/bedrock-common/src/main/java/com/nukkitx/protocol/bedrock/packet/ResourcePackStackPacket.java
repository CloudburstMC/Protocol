package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourcePackStackPacket extends BedrockPacket {
    private boolean forcedToAccept;
    private final List<Entry> behaviorPacks = new ArrayList<>();
    private final List<Entry> resourcePacks = new ArrayList<>();
    private boolean experimental;
    private String gameVersion;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Value
    public static class Entry {
        private final String packId;
        private final String packVersion;
        private final String subpackName;
    }
}
