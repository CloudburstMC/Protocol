package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.MaterialReducer;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class CraftingDataPacket implements BedrockPacket {
    private final List<RecipeData> craftingData = new ObjectArrayList<>();
    private final List<PotionMixData> potionMixData = new ObjectArrayList<>();
    private final List<ContainerMixData> containerMixData = new ObjectArrayList<>();
    /**
     * @since v465
     */
    private final List<MaterialReducer> materialReducers = new ObjectArrayList<>();
    private boolean cleanRecipes;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_DATA;
    }

    @Override
    public CraftingDataPacket clone() {
        try {
            return (CraftingDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

