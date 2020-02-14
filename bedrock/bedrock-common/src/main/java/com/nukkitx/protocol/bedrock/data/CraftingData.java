package com.nukkitx.protocol.bedrock.data;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@RequiredArgsConstructor
public class CraftingData {
    private final CraftingType type;
    private final String recipeId;
    private final int width;
    private final int height;
    private final int inputId;
    private final int inputDamage;
    private final ItemData[] inputs;
    private final ItemData[] outputs;
    private final UUID uuid;
    private final String craftingTag;
    private final int priority;

    public CraftingData(CraftingType type, int width, int height, int inputId, int inputDamage, ItemData[] inputs,
                        ItemData[] outputs, UUID uuid, String craftingTag) {
        this.type = type;
        this.recipeId = null;
        this.width = width;
        this.height = height;
        this.inputId = inputId;
        this.inputDamage = inputDamage;
        this.inputs = inputs;
        this.outputs = outputs;
        this.uuid = uuid;
        this.craftingTag = craftingTag;
        this.priority = 0;
    }

    public static CraftingData fromFurnaceData(int inputId, int inputDamage, ItemData output, String craftingTag) {
        return new CraftingData(CraftingType.FURNACE_DATA, null, -1, -1, inputId, inputDamage,
                null, new ItemData[]{output}, null, craftingTag, -1);
    }

    public static CraftingData fromFurnace(int inputId, ItemData input, String craftingTag) {
        return new CraftingData(CraftingType.FURNACE, null, -1, -1, inputId, -1,
                null, new ItemData[]{input}, null, craftingTag, -1);
    }

    public static CraftingData fromShapeless(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                             String craftingTag, int priority) {
        return new CraftingData(CraftingType.SHAPELESS, recipeId, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, priority);
    }

    public static CraftingData fromShaped(String recipeId, int width, int height, ItemData[] inputs,
                                          ItemData[] outputs, UUID uuid, String craftingTag, int priority) {
        return new CraftingData(CraftingType.SHAPED, recipeId, width, height, -1, -1, inputs,
                outputs, uuid, craftingTag, priority);
    }

    public static CraftingData fromShapelessChemistry(String recipeId, ItemData[] inputs, ItemData[] outputs,
                                                      UUID uuid, String craftingTag, int priority) {
        return new CraftingData(CraftingType.SHAPELESS_CHEMISTRY, recipeId, -1, -1, -1,
                -1, inputs, outputs, uuid, craftingTag, priority);
    }

    public static CraftingData fromShapedChemistry(String recipeId, int width, int height, ItemData[] inputs,
                                                   ItemData[] outputs, UUID uuid, String craftingTag, int priority) {
        return new CraftingData(CraftingType.SHAPED_CHEMISTRY, recipeId, width, height, -1, -1,
                inputs, outputs, uuid, craftingTag, priority);
    }

    public static CraftingData fromShulkerBox(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                              String craftingTag, int priority) {
        return new CraftingData(CraftingType.SHULKER_BOX, recipeId, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, priority);
    }

    public static CraftingData fromMulti(UUID uuid) {
        return new CraftingData(CraftingType.MULTI, null, -1, -1, -1, -1,
                null, null, uuid, null, -1);
    }

    public static CraftingData fromShapeless(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag) {
        return new CraftingData(CraftingType.SHAPELESS, "", -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag, 0);
    }

    public static CraftingData fromShaped(int width, int height, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                          String craftingTag) {
        return new CraftingData(CraftingType.SHAPED, "", width, height, -1, -1, inputs, outputs, uuid,
                craftingTag, 0);
    }

    public static CraftingData fromShapelessChemistry(ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                                      String craftingTag) {
        return new CraftingData(CraftingType.SHAPELESS_CHEMISTRY, "", -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, 0);
    }

    public static CraftingData fromShapedChemistry(int width, int height, ItemData[] inputs, ItemData[] outputs,
                                                   UUID uuid, String craftingTag) {
        return new CraftingData(CraftingType.SHAPED_CHEMISTRY, "", width, height, -1, -1, inputs,
                outputs, uuid, craftingTag, 0);
    }

    public static CraftingData fromShulkerBox(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag) {
        return new CraftingData(CraftingType.SHULKER_BOX, "", -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag, 0);
    }
}
