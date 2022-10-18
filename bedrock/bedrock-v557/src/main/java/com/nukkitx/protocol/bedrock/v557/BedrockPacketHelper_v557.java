package com.nukkitx.protocol.bedrock.v557;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityProperties;
import com.nukkitx.protocol.bedrock.data.entity.FloatEntityProperty;
import com.nukkitx.protocol.bedrock.data.entity.IntEntityProperty;
import com.nukkitx.protocol.bedrock.v554.BedrockPacketHelper_v554;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v557 extends BedrockPacketHelper_v554 {

    public static final BedrockPacketHelper_v557 INSTANCE = new BedrockPacketHelper_v557();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        // UPDATE_PROPERTIES removed
        this.entityData.put(120, EntityData.FREEZING_EFFECT_STRENGTH);
        this.entityData.put(121, EntityData.BUOYANCY_DATA);
        this.entityData.put(122, EntityData.GOAT_HORN_COUNT);
        this.entityData.put(123, EntityData.BASE_RUNTIME_ID);
        this.entityData.put(124, EntityData.MOVEMENT_SOUND_DISTANCE_OFFSET);
        this.entityData.put(125, EntityData.HEARTBEAT_INTERVAL_TICKS);
        this.entityData.put(126, EntityData.HEARTBEAT_SOUND_EVENT);
        this.entityData.put(127, EntityData.PLAYER_LAST_DEATH_POS);
        this.entityData.put(128, EntityData.PLAYER_LAST_DEATH_DIMENSION);
        this.entityData.put(129, EntityData.PLAYER_HAS_DIED);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.soundEvents.remove(443); // Remove old undefined value
        this.soundEvents.put(445, SoundEvent.BUNDLE_DROP_CONTENTS);
        this.soundEvents.put(446, SoundEvent.BUNDLE_INSERT);
        this.soundEvents.put(447, SoundEvent.BUNDLE_REMOVE_ONE);
        this.soundEvents.put(448, SoundEvent.UNDEFINED);
    }

    @Override
    public void readEntityProperties(ByteBuf buffer, EntityProperties properties) {
        readArray(buffer, properties.getIntProperties(), byteBuf -> {
            int index = VarInts.readUnsignedInt(byteBuf);
            int value = VarInts.readInt(byteBuf);
            return new IntEntityProperty(index, value);
        });
        readArray(buffer, properties.getFloatProperties(), byteBuf -> {
            int index = VarInts.readUnsignedInt(byteBuf);
            float value = byteBuf.readFloatLE();
            return new FloatEntityProperty(index, value);
        });
    }

    @Override
    public void writeEntityProperties(ByteBuf buffer, EntityProperties properties) {
        writeArray(buffer, properties.getIntProperties(), (byteBuf, property) -> {
            VarInts.writeUnsignedInt(byteBuf, property.getIndex());
            VarInts.writeInt(byteBuf, property.getValue());
        });
        writeArray(buffer, properties.getFloatProperties(), (byteBuf, property) -> {
            VarInts.writeUnsignedInt(byteBuf, property.getIndex());
            byteBuf.writeFloatLE(property.getValue());
        });
    }
}
