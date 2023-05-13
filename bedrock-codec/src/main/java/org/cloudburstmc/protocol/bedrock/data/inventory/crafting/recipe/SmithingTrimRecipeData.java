package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmithingTrimRecipeData implements TaggedCraftingData, IdentifiableRecipeData, NetworkRecipeData {

    private final String id;
    private final ItemDescriptorWithCount base;
    private final ItemDescriptorWithCount addition;
    private final ItemDescriptorWithCount template;
    private final String tag;
    private final int netId;

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.SMITHING_TRIM;
    }

    public static SmithingTrimRecipeData of(String id, ItemDescriptorWithCount base, ItemDescriptorWithCount addition,
                                            ItemDescriptorWithCount template, String tag, int netId) {
        return new SmithingTrimRecipeData(id, base, addition, template, tag, netId);
    }
}
