package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmithingTransformRecipeData implements TaggedCraftingData, IdentifiableRecipeData, NetworkRecipeData {

    private final String id;
    private final ItemDescriptorWithCount template;
    private final ItemDescriptorWithCount base;
    private final ItemDescriptorWithCount addition;
    private final ItemData result;
    private final String tag;
    private final int netId;

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.SMITHING_TRANSFORM;
    }

    public static SmithingTransformRecipeData of(String id, ItemDescriptorWithCount template, ItemDescriptorWithCount base,
                                                 ItemDescriptorWithCount addition, ItemData result, String tag, int netId) {
        return new SmithingTransformRecipeData(id, template, base, addition, result, tag, netId);
    }

    /**
     * Pre-1.19.80
     */
    public static SmithingTransformRecipeData of(String id, ItemDescriptorWithCount base,
                                                 ItemDescriptorWithCount addition, ItemData result, String tag, int netId) {
        return new SmithingTransformRecipeData(id, null, base, addition, result, tag, netId);
    }
}
