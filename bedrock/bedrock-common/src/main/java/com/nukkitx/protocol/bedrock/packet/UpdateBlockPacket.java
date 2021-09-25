package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class UpdateBlockPacket extends BedrockPacket {
    public static final Set<Flag> FLAG_ALL = Collections.unmodifiableSet(EnumSet.of(Flag.NEIGHBORS, Flag.NETWORK));
    public static final Set<Flag> FLAG_ALL_PRIORITY = Collections.unmodifiableSet(
            EnumSet.of(Flag.NEIGHBORS, Flag.NETWORK, Flag.PRIORITY));

    private final Set<Flag> flags = EnumSet.noneOf(Flag.class);
    private Vector3i blockPosition;
    private int runtimeId;
    private int dataLayer;

    public UpdateBlockPacket addFlag(Flag flag) {
        this.flags.add(flag);
        return this;
    }

    public UpdateBlockPacket addFlags(Flag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public UpdateBlockPacket addFlagAll() {
        this.flags.addAll(FLAG_ALL);
        return this;
    }

    public UpdateBlockPacket addFlagAllPriority() {
        this.flags.addAll(FLAG_ALL_PRIORITY);
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_BLOCK;
    }

    public enum Flag {
        NEIGHBORS,
        NETWORK,
        NO_GRAPHIC,
        UNUSED,
        PRIORITY
    }
}
