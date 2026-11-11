package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.RecipeUnlockingRequirement;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.List;
import java.util.UUID;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ShapedRecipeData implements CraftingRecipeData {

    private final CraftingDataType type;
    private final String id;
    private final int width;
    private final int height;
    private final List<ItemDescriptorWithCount> ingredients;
    private final List<ItemData> results;
    private final UUID uuid;
    private final String tag;
    private final int priority;
    private final int netId;
    /**
     * @since v671
     */
    private final boolean assumeSymmetry;
    /**
     * @since v685
     */
    private final RecipeUnlockingRequirement requirement;

    public static ShapedRecipeData of(CraftingDataType type, String id, int width, int height,
                                      List<ItemDescriptorWithCount> ingredients, List<ItemData> results, UUID uuid,
                                      String tag, int priority, int netId) {
        return ShapedRecipeData.of(type, id, width, height, ingredients, results, uuid, tag, priority, netId, false,
                RecipeUnlockingRequirement.INVALID);
    }

    public static ShapedRecipeData of(CraftingDataType type, String id, int width, int height,
                                      List<ItemDescriptorWithCount> ingredients, List<ItemData> results, UUID uuid,
                                      String tag, int priority, int netId, boolean assumeSymmetry, RecipeUnlockingRequirement requirement) {
        checkArgument(type == CraftingDataType.SHAPED || type == CraftingDataType.SHAPED_CHEMISTRY,
                "type must be SHAPED or SHAPED_CHEMISTRY");
        return new ShapedRecipeData(type, id, width, height, ingredients, results, uuid, tag, priority, netId, assumeSymmetry, requirement);
    }

    public static ShapedRecipeData of(CraftingDataType type, String id, int width, int height,
                                      List<ItemDescriptorWithCount> ingredients, List<ItemData> results, UUID uuid,
                                      String tag, int priority, int netId, boolean assumeSymmetry) {
        return ShapedRecipeData.of(type, id, width, height, ingredients, results, uuid, tag, priority, netId, assumeSymmetry,
                RecipeUnlockingRequirement.INVALID);
    }

    public static ShapedRecipeData shaped(String id, int width, int height, List<ItemDescriptorWithCount> ingredients,
                                          List<ItemData> results, UUID uuid, String tag, int priority, int netId, boolean assumeSymmetry,
                                          RecipeUnlockingRequirement requirement) {
        return of(CraftingDataType.SHAPED, id, width, height, ingredients, results, uuid, tag, priority, netId, assumeSymmetry, requirement);
    }

    public static ShapedRecipeData shapedChemistry(String id, int width, int height,
                                                   List<ItemDescriptorWithCount> ingredients, List<ItemData> results,
                                                   UUID uuid, String tag, int priority, int netId, boolean assumeSymmetry) {
        return of(CraftingDataType.SHAPED_CHEMISTRY, id, width, height, ingredients, results, uuid, tag, priority, netId,
                assumeSymmetry);
    }
}
