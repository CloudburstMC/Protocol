package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlags;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddItemEntityPacket extends BedrockPacket {
    private final EntityDataMap metadata = new EntityDataMap();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private ItemData itemInHand;
    private Vector3f position;
    private Vector3f motion;
    private boolean fromFishing;

    public AddItemEntityPacket putEntityDataByte(EntityData key, int value) {
        this.metadata.putByte(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataShort(EntityData key, int value) {
        this.metadata.putShort(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataInt(EntityData key, int value) {
        this.metadata.putInt(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataFloat(EntityData key, float value) {
        this.metadata.putFloat(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataString(EntityData key, String value) {
        this.metadata.putString(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataTag(EntityData key, NbtMap value) {
        this.metadata.putTag(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataPos(EntityData key, Vector3i value) {
        this.metadata.putPos(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataLong(EntityData key, long value) {
        this.metadata.putLong(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataVector3f(EntityData key, Vector3f value) {
        this.metadata.putVector3f(key, value);
        return this;
    }

    public AddItemEntityPacket putEntityDataFlags(EntityFlags flags) {
        this.metadata.putFlags(flags);
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_ITEM_ENTITY;
    }
}
