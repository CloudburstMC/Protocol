package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AdventureSettingsPacket extends BedrockPacket {
    private final Set<AdventureSetting> settings = new ObjectLinkedOpenHashSet<>();
    private CommandPermission commandPermission = CommandPermission.NORMAL;
    private PlayerPermission playerPermission = PlayerPermission.VISITOR;
    private long uniqueEntityId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADVENTURE_SETTINGS;
    }
}
