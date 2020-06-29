package com.nukkitx.protocol.bedrock.data.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.AutoCraftRecipeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.BeaconPaymentStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.ConsumeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftCreativeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftNonImplementedStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftResultsDeprecatedStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CreateStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.DestroyStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.DropStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.LabTableCombineRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.PlaceStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.SwapStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.TakeStackRequestActionData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum StackRequestActionType {
    TAKE(TakeStackRequestActionData.class),
    PLACE(PlaceStackRequestActionData.class),
    SWAP(SwapStackRequestActionData.class),
    DROP(DropStackRequestActionData.class),
    DESTROY(DestroyStackRequestActionData.class),
    CONSUME(ConsumeStackRequestActionData.class),
    CREATE(CreateStackRequestActionData.class),
    LAB_TABLE_COMBINE(LabTableCombineRequestActionData.class),
    BEACON_PAYMENT(BeaconPaymentStackRequestActionData.class),
    CRAFT_RECIPE(CraftRecipeStackRequestActionData.class),
    CRAFT_RECIPE_AUTO(AutoCraftRecipeStackRequestActionData.class),
    CRAFT_CREATIVE(CraftCreativeStackRequestActionData.class),
    CRAFT_NON_IMPLEMENTED_DEPRECATED(CraftNonImplementedStackRequestActionData.class),
    CRAFT_RESULTS_DEPRECATED(CraftResultsDeprecatedStackRequestActionData.class);

    private static final StackRequestActionType[] VALUES = values();
    private static final Map<Class<? extends StackRequestActionData>, StackRequestActionType> CLASSES = Arrays.stream(values())
            .collect(Collectors.toMap(StackRequestActionType::getCls, v -> v));

    private final Class<? extends StackRequestActionData> cls;

    public static StackRequestActionType byId(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        }
        throw new UnsupportedOperationException("Unknown StackRequestActionType ID: " + id);
    }

    public static StackRequestActionType byClass(Class<? extends StackRequestActionData> cls) {
        if (CLASSES.containsKey(cls)) {
            return CLASSES.get(cls);
        }
        throw new UnsupportedOperationException("Unknown StackRequestActionType Class: " + cls.getSimpleName());
    }
}
