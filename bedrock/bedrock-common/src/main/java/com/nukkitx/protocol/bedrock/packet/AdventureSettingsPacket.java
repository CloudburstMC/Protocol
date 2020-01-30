package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CommandPermission;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdventureSettingsPacket extends BedrockPacket {
    private final Set<Flag> flags = new HashSet<>();
    private CommandPermission commandPermission;
    private PlayerPermission playerPermission;
    private long uniqueEntityId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADVENTURE_SETTINGS;
    }

    public enum Flag {
        IMMUTABLE_WORLD, // 0x1
        NO_PVP, // 0x2
        NO_PVM, // 0x4
        NO_MVP, // 0x10
        AUTO_JUMP, // 0x20
        BUILD, // 2 - 0x100
        MINE, // 2 - 0x1
        DOORS_AND_SWITCHES, // 2 - 0x2
        OPEN_CONTAINERS, // 2 - 0x4
        ATTACK_PLAYERS, // 2 - 0x8
        ATTACK_MOBS, // 2 - 0x10
        OP, // 2 - 0x20
        TELEPORT, // 2 - 0x80
        SET_DEFAULT, // 2 - 0x200
        FLYING, // 0x200
        MAY_FLY, // 0x40
        MUTE, // 0x400
        WORLD_BUILDER, // 0x100
        NO_CLIP // 0x80
    }
}
