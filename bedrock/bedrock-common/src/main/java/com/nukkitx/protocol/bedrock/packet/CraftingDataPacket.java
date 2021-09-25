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
import lombok.experimental.Accessors;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
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

    public CraftingDataPacket addCraftingData(CraftingData craftingData) {
        this.craftingData.add(craftingData);
        return this;
    }

    public CraftingDataPacket addCraftingData(CraftingData... craftingData) {
        this.craftingData.addAll(Arrays.asList(craftingData));
        return this;
    }

    public CraftingDataPacket addPotionMixData(PotionMixData potionMixData) {
        this.potionMixData.add(potionMixData);
        return this;
    }

    public CraftingDataPacket addPotionMixData(PotionMixData... potionMixData) {
        this.potionMixData.addAll(Arrays.asList(potionMixData));
        return this;
    }

    public CraftingDataPacket addContainerMixData(ContainerMixData containerMixData) {
        this.containerMixData.add(containerMixData);
        return this;
    }

    public CraftingDataPacket addContainerMixData(ContainerMixData... containerMixData) {
        this.containerMixData.addAll(Arrays.asList(containerMixData));
        return this;
    }

    public CraftingDataPacket addMaterialReducer(MaterialReducer materialReducer) {
        this.materialReducers.add(materialReducer);
        return this;
    }

    public CraftingDataPacket addMaterialReducers(MaterialReducer... materialReducers) {
        this.materialReducers.addAll(Arrays.asList(materialReducers));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_DATA;
    }
}
