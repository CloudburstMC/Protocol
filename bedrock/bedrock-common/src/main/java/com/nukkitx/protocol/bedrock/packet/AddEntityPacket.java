package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlags;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddEntityPacket extends BedrockPacket {
    private final List<AttributeData> attributes = new ObjectArrayList<>();
    private final EntityDataMap metadata = new EntityDataMap();
    private final List<EntityLinkData> entityLinks = new ObjectArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private String identifier;
    private int entityType;
    private Vector3f position;
    private Vector3f motion;
    private Vector3f rotation;

    public AddEntityPacket addAttribute(AttributeData attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public AddEntityPacket addAttributes(AttributeData... attributes) {
        this.attributes.addAll(Arrays.asList(attributes));
        return this;
    }

    public AddEntityPacket putEntityDataByte(EntityData key, int value) {
        this.metadata.putByte(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataShort(EntityData key, int value) {
        this.metadata.putShort(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataInt(EntityData key, int value) {
        this.metadata.putInt(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataFloat(EntityData key, float value) {
        this.metadata.putFloat(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataString(EntityData key, String value) {
        this.metadata.putString(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataTag(EntityData key, NbtMap value) {
        this.metadata.putTag(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataPos(EntityData key, Vector3i value) {
        this.metadata.putPos(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataLong(EntityData key, long value) {
        this.metadata.putLong(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataVector3f(EntityData key, Vector3f value) {
        this.metadata.putVector3f(key, value);
        return this;
    }

    public AddEntityPacket putEntityDataFlags(EntityFlags flags) {
        this.metadata.putFlags(flags);
        return this;
    }

    public AddEntityPacket addEntityLink(EntityLinkData entityLink) {
        this.entityLinks.add(entityLink);
        return this;
    }

    public AddEntityPacket addEntityLinks(EntityLinkData... entityLinks) {
        this.entityLinks.addAll(Arrays.asList(entityLinks));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_ENTITY;
    }
}
