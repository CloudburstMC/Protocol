package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiRecipeData implements UniqueCraftingData {

    private final UUID uuid;
    private final int netId;

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.MULTI;
    }

    public static MultiRecipeData of(UUID uuid, int netId) {
        return new MultiRecipeData(uuid, netId);
    }
}
