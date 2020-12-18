package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.Value;

/**
 * CraftResultsDeprecatedStackRequestAction is an additional, deprecated packet sent by the client after
 * crafting. It holds the final results and the amount of times the recipe was crafted. It shouldn't be used.
 * This action is also sent when an item is enchanted. Enchanting should be treated mostly the same way as
 * crafting, where the old item is consumed.
 */
@Value
public class CraftResultsDeprecatedStackRequestActionData extends StackRequestActionData {
    ItemData[] resultItems;
    byte timesCrafted;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_RESULTS_DEPRECATED;
    }
}
