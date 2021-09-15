package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingData;
import org.cloudburstmc.protocol.bedrock.data.inventory.MaterialReducer;
import org.cloudburstmc.protocol.bedrock.data.inventory.PotionMixData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class CraftingDataPacket implements BedrockPacket {
    private final List<CraftingData> craftingData = new ObjectArrayList<>();
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
}
