package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventPacket extends BedrockPacket {
    protected long uniqueEntityId;
    protected int data;
    protected int type;
    //protected EventDetails details; Haven't quite figured this out yet.

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Event {
        ACHIEVEMENT_AWARDED,
        ENTITY_INTERACT,
        PORTAL_BUILT,
        PORTAL_USED,
        MOB_KILLED,
        CAULDRON_USED,
        PLAYER_DEATH,
        BOSS_KILLED,
        AGENT_COMMAND,
        AGENT_CREATED,
        PATTERN_REMOVED,
        COMMAND_EXECUTED,
        FISH_BUCKETED
    }
}
