package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.bedrock.data.AbilityLayer;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;

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