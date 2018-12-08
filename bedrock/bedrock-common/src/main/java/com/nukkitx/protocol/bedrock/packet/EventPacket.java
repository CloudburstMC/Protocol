package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventPacket extends BedrockPacket {
    private long uniqueEntityId;
    private int data;
    private int type;

    private int id;
    private int cause;
    private int unknown0;
    private short unknown1;
    private long mobEntityId;
    private long unknownEid;
    private String unknown2;
    private String unknown3;
    private String unknown4;
    private int unknown5;
    private int unknown6;
    private String unknown7;
    private boolean unknown8;

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
