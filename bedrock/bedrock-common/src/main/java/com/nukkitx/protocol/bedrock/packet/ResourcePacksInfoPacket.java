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
public class ResourcePacksInfoPacket extends BedrockPacket {
    protected final List<Entry> behaviorPackInfos = new ArrayList<>();
    protected final List<Entry> resourcePackInfos = new ArrayList<>();
    protected boolean forcedToAccept;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Value
    public static class Entry {
        private final UUID packId;
        private final String packVersion;
        private final long packSize;
        private final String encryptionKey;
        private final String subpackName;
        private final String contentId;
    }
}
