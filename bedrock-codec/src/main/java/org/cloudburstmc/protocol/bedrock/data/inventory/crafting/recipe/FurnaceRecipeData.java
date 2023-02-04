package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FurnaceRecipeData implements TaggedCraftingData {

    private final CraftingDataType type;
    private final int inputId;
    private final int inputData;
    private final ItemData result;
    private final String tag;

    public boolean hasData() {
        return type == CraftingDataType.FURNACE_DATA;
    }

    public static FurnaceRecipeData of(CraftingDataType type, int inputId, int inputData, ItemData result, String tag) {
        checkArgument(type == CraftingDataType.FURNACE || type == CraftingDataType.FURNACE_DATA,
                "type must be FURNACE or FURNACE_DATA");
        return new FurnaceRecipeData(type, inputId, inputData, result, tag);
    }

    public static FurnaceRecipeData of(int inputId, ItemData result, String tag) {
        return new FurnaceRecipeData(CraftingDataType.FURNACE, inputId, -1, result, tag);
    }

    public static FurnaceRecipeData of(int inputId, int inputData, ItemData result, String tag) {
        return new FurnaceRecipeData(CraftingDataType.FURNACE_DATA, inputId, inputData, result, tag);
    }
}
