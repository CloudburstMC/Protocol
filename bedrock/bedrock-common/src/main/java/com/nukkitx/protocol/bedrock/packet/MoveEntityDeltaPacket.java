package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class MoveEntityDeltaPacket extends BedrockPacket {
    private long runtimeEntityId;

    private final Set<Flag> flags = EnumSet.noneOf(Flag.class);

    private int deltaX;
    private int deltaY;
    private int deltaZ;

    private float x;
    private float y;
    private float z;

    private float pitch;
    private float yaw;
    private float headYaw;

    public MoveEntityDeltaPacket addFlag(Flag flag) {
        this.flags.add(flag);
        return this;
    }

    public MoveEntityDeltaPacket addFlags(Flag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVE_ENTITY_DELTA;
    }

    public String toString() {
        return "MoveEntityDeltaPacket(runtimeEntityId=" + runtimeEntityId +
                ", flags=" + flags + ", delta=(" + deltaX + ", " + deltaY + ", " + deltaZ +
                "), position=(" + x + ", " + y + ", " + z +
                "), rotation=(" + pitch + ", " + yaw + ", " + headYaw + "))";
    }

    public enum Flag {
        HAS_X,
        HAS_Y,
        HAS_Z,
        HAS_PITCH,
        HAS_YAW,
        HAS_HEAD_YAW,
        ON_GROUND,
        TELEPORTING,
        FORCE_MOVE_LOCAL_ENTITY
    }
}
