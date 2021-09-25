package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.EnchantOptionData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PlayerEnchantOptionsPacket extends BedrockPacket {
    private final List<EnchantOptionData> options = new ArrayList<>();

    public PlayerEnchantOptionsPacket addOption(EnchantOptionData option) {
        this.options.add(option);
        return this;
    }

    public PlayerEnchantOptionsPacket addOptions(EnchantOptionData... options) {
        this.options.addAll(Arrays.asList(options));
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ENCHANT_OPTIONS;
    }
}
