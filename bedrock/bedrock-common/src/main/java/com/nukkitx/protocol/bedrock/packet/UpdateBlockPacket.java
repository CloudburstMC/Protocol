package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateBlockPacket extends BedrockPacket {
    public static final Set<Flag> FLAG_ALL = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(Flag.NEIGHBORS, Flag.NETWORK)));
    public static final Set<Flag> FLAG_ALL_PRIORITY = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(Flag.NEIGHBORS, Flag.NETWORK, Flag.PRIORITY)));
    protected final Set<Flag> flags = new HashSet<>();
    protected Vector3i blockPosition;
    protected int runtimeId;
    protected int dataLayer;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Flag {
        NONE,
        NEIGHBORS,
        NETWORK,
        NO_GRAPHIC,
        PRIORITY
    }
}
