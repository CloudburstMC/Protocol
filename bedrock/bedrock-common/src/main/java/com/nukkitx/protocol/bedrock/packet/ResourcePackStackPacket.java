package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ResourcePackStackPacket extends BedrockPacket {
    private boolean forcedToAccept;
    private final List<Entry> behaviorPacks = new ObjectArrayList<>();
    private final List<Entry> resourcePacks = new ObjectArrayList<>();
    private boolean experimental;
    private String gameVersion;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_STACK;
    }

    @Value
    public static class Entry {
        private final String packId;
        private final String packVersion;
        private final String subpackName;
    }
}
