package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AddPlayerPacket implements BedrockPacket, PlayerAbilityHolder {
    private EntityDataMap metadata = new EntityDataMap();
    private List<EntityLinkData> entityLinks = new ObjectArrayList<>();
    private UUID uuid;
    private String username;
    private long uniqueEntityId;
    private long runtimeEntityId;
    private String platformChatId;
    private Vector3f position;
    private Vector3f motion;
    private Vector3f rotation;
    private ItemData hand;
    private AdventureSettingsPacket adventureSettings = new AdventureSettingsPacket();
    private String deviceId;
    private int buildPlatform;
    private GameType gameType;

    /**
     * @since v534
     */
    private List<AbilityLayer> abilityLayers = new ObjectArrayList<>();
    /**
     * @since v557
     */
    private final EntityProperties properties = new EntityProperties();

    public void setUniqueEntityId(long uniqueEntityId) {
        this.uniqueEntityId = uniqueEntityId;
        this.adventureSettings.setUniqueEntityId(uniqueEntityId);
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

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_PLAYER;
    }

    @Override
    public AddPlayerPacket clone() {
        try {
            return (AddPlayerPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

