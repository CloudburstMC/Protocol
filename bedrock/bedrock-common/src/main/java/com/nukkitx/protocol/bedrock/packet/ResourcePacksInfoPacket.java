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
public class ResourcePacksInfoPacket extends BedrockPacket {
    private final List<Entry> behaviorPackInfos = new ObjectArrayList<>();
    private final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    private boolean forcedToAccept;
    private boolean scriptingEnabled;
    private boolean forcingServerPacksEnabled;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACKS_INFO;
    }

    @Value
    public static class Entry {
        String packId;
        String packVersion;
        long packSize;
        String contentKey;
        String subPackName;
        String contentId;
        boolean scripting;
        boolean raytracingCapable;
    }
}
