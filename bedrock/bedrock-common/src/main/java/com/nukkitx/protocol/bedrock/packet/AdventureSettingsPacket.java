package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AdventureSettingsPacket extends BedrockPacket {
    private final Set<AdventureSetting> settings = EnumSet.noneOf(AdventureSetting.class);
    private CommandPermission commandPermission = CommandPermission.NORMAL;
    private PlayerPermission playerPermission = PlayerPermission.VISITOR;
    private long uniqueEntityId;

    public AdventureSettingsPacket addSetting(AdventureSetting setting) {
        this.settings.add(setting);
        return this;
    }

    public AdventureSettingsPacket addSettings(AdventureSetting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADVENTURE_SETTINGS;
    }
}
