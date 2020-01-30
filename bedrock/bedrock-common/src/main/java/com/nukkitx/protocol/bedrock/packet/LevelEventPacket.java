package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LevelEventPacket extends BedrockPacket {
    private Event event;
    private Vector3f position;
    private int data;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_EVENT;
    }

    public enum Event {
        SOUND_CLICK,
        SOUND_CLICK_FAIL,
        SOUND_SHOOT,
        SOUND_DOOR,
        SOUND_FIZZ,
        SOUND_IGNITE,
        SOUND_GHAST,
        SOUND_GHAST_SHOOT,
        SOUND_BLAZE_SHOOT,
        SOUND_DOOR_BUMP,
        SOUND_DOOR_CRASH,
        SOUND_ENDERMAN_TELEPORT,
        SOUND_ANVIL_BREAK,
        SOUND_ANVIL_USE,
        SOUND_ANVIL_FALL,
        SOUND_POP,
        SOUND_PORTAL,
        SOUND_ITEMFRAME_ADD_ITEM,
        SOUND_ITEMFRAME_REMOVE,
        SOUND_ITEMFRAME_PLACE,
        SOUND_ITEMFRAME_REMOVE_ITEM,
        SOUND_ITEMFRAME_ROTATE_ITEM,
        SOUND_CAMERA,
        SOUND_ORB,
        SOUND_TOTEM,
        SOUND_ARMOR_STAND_BREAK,
        SOUND_ARMOR_STAND_HIT,
        SOUND_ARMOR_STAND_FALL,
        SOUND_ARMOR_STAND_PLACE,
        PARTICLE_SHOOT,
        PARTICLE_DESTROY,
        PARTICLE_SPLASH,
        PARTICLE_EYE_DESPAWN,
        PARTICLE_SPAWN,
        GUARDIAN_CURSE,
        PARTICLE_BLOCK_FORCE_FIELD,
        PARTICLE_PUNCH_BLOCK,
        PARTICLE_WATER_SPLASH,
        START_RAIN,
        START_THUNDER,
        STOP_RAIN,
        STOP_THUNDER,
        PAUSE_GAME, //data: 1 to pause, 0 to resume
        REDSTONE_TRIGGER,
        CAULDRON_EXPLODE,
        CAULDRON_DYE_ARMOR,
        CAULDRON_CLEAN_ARMOR,
        CAULDRON_FILL_POTION,
        CAULDRON_TAKE_POTION,
        CAULDRON_FILL_WATER,
        CAULDRON_TAKE_WATER,
        CAULDRON_ADD_DYE,
        CAULDRON_CLEAN_BANNER,
        BLOCK_START_BREAK,
        BLOCK_STOP_BREAK,
        BLOCK_CONTINUE_BREAK,
        SET_DATA,
        PLAYERS_SLEEPING
    }
}
