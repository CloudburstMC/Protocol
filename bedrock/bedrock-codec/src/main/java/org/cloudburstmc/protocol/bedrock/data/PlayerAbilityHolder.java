package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;

import java.util.List;

public interface PlayerAbilityHolder {

    long getUniqueEntityId();
    void setUniqueEntityId(long uniqueEntityId);

    PlayerPermission getPlayerPermission();
    void setPlayerPermission(PlayerPermission playerPermission);

    CommandPermission getCommandPermission();
    void setCommandPermission(CommandPermission commandPermission);

    List<AbilityLayer> getAbilityLayers();
    void setAbilityLayers(List<AbilityLayer> layers);
}
