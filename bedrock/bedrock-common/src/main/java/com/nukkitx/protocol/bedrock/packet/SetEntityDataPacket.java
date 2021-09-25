package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlags;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetEntityDataPacket extends BedrockPacket {
    private final EntityDataMap metadata = new EntityDataMap();
    private long runtimeEntityId;
    private long tick;

    public SetEntityDataPacket putEntityDataByte(EntityData key, int value) {
        this.metadata.putByte(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataShort(EntityData key, int value) {
        this.metadata.putShort(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataInt(EntityData key, int value) {
        this.metadata.putInt(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataFloat(EntityData key, float value) {
        this.metadata.putFloat(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataString(EntityData key, String value) {
        this.metadata.putString(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataTag(EntityData key, NbtMap value) {
        this.metadata.putTag(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataPos(EntityData key, Vector3i value) {
        this.metadata.putPos(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataLong(EntityData key, long value) {
        this.metadata.putLong(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataVector3f(EntityData key, Vector3f value) {
        this.metadata.putVector3f(key, value);
        return this;
    }

    public SetEntityDataPacket putEntityDataFlags(EntityFlags flags) {
        this.metadata.putFlags(flags);
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_ENTITY_DATA;
    }
}
