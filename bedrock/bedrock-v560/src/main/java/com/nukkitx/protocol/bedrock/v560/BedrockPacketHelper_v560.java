package com.nukkitx.protocol.bedrock.v560;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.v557.BedrockPacketHelper_v557;

public class BedrockPacketHelper_v560 extends BedrockPacketHelper_v557 {

    public static final BedrockPacketHelper_v560 INSTANCE = new BedrockPacketHelper_v560();

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.entityFlags.put(46, EntityFlag.CAN_DASH);
        this.entityFlags.put(108, EntityFlag.HAS_DASH_COOLDOWN);
        this.entityFlags.put(109, EntityFlag.PUSH_TOWARDS_CLOSEST_SPACE);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.soundEvents.remove(448); // UNDEFINED
        this.soundEvents.put(448, SoundEvent.PRESSURE_PLATE_CLICK_OFF);
        this.soundEvents.put(449, SoundEvent.PRESSURE_PLATE_CLICK_ON);
        this.soundEvents.put(450, SoundEvent.BUTTON_CLICK_OFF);
        this.soundEvents.put(451, SoundEvent.BUTTON_CLICK_ON);
        this.soundEvents.put(452, SoundEvent.DOOR_OPEN);
        this.soundEvents.put(453, SoundEvent.DOOR_CLOSE);
        this.soundEvents.put(454, SoundEvent.TRAPDOOR_OPEN);
        this.soundEvents.put(455, SoundEvent.TRAPDOOR_CLOSE);
        this.soundEvents.put(456, SoundEvent.FENCE_GATE_OPEN);
        this.soundEvents.put(457, SoundEvent.FENCE_GATE_CLOSE);
        this.soundEvents.put(458, SoundEvent.UNDEFINED);
    }
}
