package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlags;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddPlayerPacket extends BedrockPacket {
    private final EntityDataMap metadata = new EntityDataMap();
    private final List<EntityLinkData> entityLinks = new ObjectArrayList<>();
    private UUID uuid;
    private String username;
    private long uniqueEntityId;
    private long runtimeEntityId;
    private String platformChatId;
    private Vector3f position;
    private Vector3f motion;
    private Vector3f rotation;
    private ItemData hand;
    private final AdventureSettingsPacket adventureSettings = new AdventureSettingsPacket();
    private String deviceId;
    private int buildPlatform;

    public AddPlayerPacket putEntityDataByte(EntityData key, int value) {
        this.metadata.putByte(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataShort(EntityData key, int value) {
        this.metadata.putShort(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataInt(EntityData key, int value) {
        this.metadata.putInt(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataFloat(EntityData key, float value) {
        this.metadata.putFloat(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataString(EntityData key, String value) {
        this.metadata.putString(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataTag(EntityData key, NbtMap value) {
        this.metadata.putTag(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataPos(EntityData key, Vector3i value) {
        this.metadata.putPos(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataLong(EntityData key, long value) {
        this.metadata.putLong(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataVector3f(EntityData key, Vector3f value) {
        this.metadata.putVector3f(key, value);
        return this;
    }

    public AddPlayerPacket putEntityDataFlags(EntityFlags flags) {
        this.metadata.putFlags(flags);
        return this;
    }

    public AddPlayerPacket addEntityLink(EntityLinkData entityLink) {
        this.entityLinks.add(entityLink);
        return this;
    }

    public AddPlayerPacket addEntityLinks(EntityLinkData... entityLinks) {
        this.entityLinks.addAll(Arrays.asList(entityLinks));
        return this;
    }

    public AddPlayerPacket setUniqueEntityId(long uniqueEntityId) {
        this.uniqueEntityId = uniqueEntityId;
        this.adventureSettings.setUniqueEntityId(uniqueEntityId);
        return this;
    }

    public AddPlayerPacket addAdventureSetting(AdventureSetting setting) {
        this.adventureSettings.addSetting(setting);
        return this;
    }

    public AddPlayerPacket addAdventureSettings(AdventureSetting... settings) {
        this.adventureSettings.addSettings(settings);
        return this;
    }

    public AddPlayerPacket setCommandPermission(CommandPermission commandPermission) {
        this.adventureSettings.setCommandPermission(commandPermission);
        return this;
    }

    public AddPlayerPacket setPlayerPermission(PlayerPermission playerPermission) {
        this.adventureSettings.setPlayerPermission(playerPermission);
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_PLAYER;
    }
}
