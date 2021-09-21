package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerMixData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.MaterialReducer;
import com.nukkitx.protocol.bedrock.data.inventory.PotionMixData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class CraftingDataPacket extends BedrockPacket {
    private final List<CraftingData> craftingData = new ObjectArrayList<>();
    private final List<PotionMixData> potionMixData = new ObjectArrayList<>();
    private final List<ContainerMixData> containerMixData = new ObjectArrayList<>();
    /**
     * @since v465
     */
    private final List<MaterialReducer> materialReducers = new ObjectArrayList<>();
    private boolean cleanRecipes;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_DATA;
    }
}
