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

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ResourcePacksInfoPacket extends BedrockPacket {
    private final List<Entry> behaviorPackInfos = new ObjectArrayList<>();
    private final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    private boolean forcedToAccept;
    private boolean scriptingEnabled;
    private boolean forcingServerPacksEnabled;

    public ResourcePacksInfoPacket addBehaviorPackInfo(Entry behaviorPackInfo) {
        this.behaviorPackInfos.add(behaviorPackInfo);
        return this;
    }

    public ResourcePacksInfoPacket addBehaviorPackInfos(Entry... behaviorPackInfos) {
        this.behaviorPackInfos.addAll(Arrays.asList(behaviorPackInfos));
        return this;
    }

    public ResourcePacksInfoPacket addResourcePackInfo(Entry resourcePackInfo) {
        this.resourcePackInfos.add(resourcePackInfo);
        return this;
    }

    public ResourcePacksInfoPacket addResourcePackInfos(Entry... resourcePackInfos) {
        this.resourcePackInfos.addAll(Arrays.asList(resourcePackInfos));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACKS_INFO;
    }

    @Value
    public static class Entry {
        private final String packId;
        private final String packVersion;
        private final long packSize;
        private final String contentKey;
        private final String subPackName;
        private final String contentId;
        private final boolean scripting;
        private final boolean raytracingCapable;
    }
}
