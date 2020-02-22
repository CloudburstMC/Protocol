package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ContainerMixData;
import com.nukkitx.protocol.bedrock.data.CraftingData;
import com.nukkitx.protocol.bedrock.data.PotionMixData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"craftingData"})
@EqualsAndHashCode(callSuper = false)
public class CraftingDataPacket extends BedrockPacket {
    private final List<CraftingData> craftingData = new ArrayList<>();
    private final List<PotionMixData> potionMixData = new ArrayList<>();
    private final List<ContainerMixData> containerMixData = new ArrayList<>();
    private boolean cleanRecipes;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_DATA;
    }
}
