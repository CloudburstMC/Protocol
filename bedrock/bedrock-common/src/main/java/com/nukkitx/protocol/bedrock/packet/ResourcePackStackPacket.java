package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ExperimentData;
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
public class ResourcePackStackPacket extends BedrockPacket {
    private boolean forcedToAccept;
    private final List<Entry> behaviorPacks = new ObjectArrayList<>();
    private final List<Entry> resourcePacks = new ObjectArrayList<>();
    private String gameVersion;
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    private boolean experimentsPreviouslyToggled;

    public ResourcePackStackPacket addBehaviorPack(Entry behaviorPack) {
        this.behaviorPacks.add(behaviorPack);
        return this;
    }

    public ResourcePackStackPacket addBehaviorPacks(Entry... behaviorPacks) {
        this.behaviorPacks.addAll(Arrays.asList(behaviorPacks));
        return this;
    }

    public ResourcePackStackPacket addResourcePack(Entry resourcePack) {
        this.resourcePacks.add(resourcePack);
        return this;
    }

    public ResourcePackStackPacket addResourcePacks(Entry... resourcePacks) {
        this.resourcePacks.addAll(Arrays.asList(resourcePacks));
        return this;
    }

    public ResourcePackStackPacket addExperiment(ExperimentData experiment) {
        this.experiments.add(experiment);
        return this;
    }

    public ResourcePackStackPacket addExperiments(ExperimentData... experiment) {
        this.experiments.addAll(Arrays.asList(experiment));
        return this;
    }

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
        private final String subPackName;
    }
}
