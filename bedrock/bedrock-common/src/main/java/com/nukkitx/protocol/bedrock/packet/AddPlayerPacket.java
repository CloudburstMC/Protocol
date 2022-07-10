package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.PlayerAbilityHolder;
import com.nukkitx.protocol.bedrock.data.AbilityLayer;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddPlayerPacket extends BedrockPacket implements PlayerAbilityHolder {
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
    /**
     * @since v503
     */
    private GameType gameType;
    /**
     * @since v534
     */
    private List<AbilityLayer> abilityLayers = new ObjectArrayList<>();

    public void setUniqueEntityId(long uniqueEntityId) {
        this.uniqueEntityId = uniqueEntityId;
        this.adventureSettings.setUniqueEntityId(uniqueEntityId);
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public PlayerPermission getPlayerPermission() {
        return this.adventureSettings.getPlayerPermission();
    }

    @Override
    public void setPlayerPermission(PlayerPermission playerPermission) {
        this.adventureSettings.setPlayerPermission(playerPermission);
    }

    @Override
    public CommandPermission getCommandPermission() {
        return this.adventureSettings.getCommandPermission();
    }

    @Override
    public void setCommandPermission(CommandPermission commandPermission) {
        this.adventureSettings.setCommandPermission(commandPermission);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_PLAYER;
    }
}
