package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@RequiredArgsConstructor
public class CraftingData {
    private final CraftingDataType type;
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
    private final int networkId;

    // @Todo changed networkId to id to match rest of code

    public CraftingData(CraftingDataType type, String recipeId, int width, int height, int inputId, int inputDamage, ItemData[] inputs,
                        ItemData[] outputs, UUID uuid, String craftingTag, int priority) {
        this(type, recipeId, width, height, inputId, inputDamage, inputs, outputs, uuid, craftingTag, priority, -1);
    }

    public CraftingData(CraftingDataType type, int width, int height, int inputId, int inputDamage, ItemData[] inputs,
                        ItemData[] outputs, UUID uuid, String craftingTag, int networkId) {
        this(type, null, width, height, inputId, inputDamage, inputs, outputs, uuid, craftingTag, 0, networkId);
    }

    public CraftingData(CraftingDataType type, int width, int height, int inputId, int inputDamage, ItemData[] inputs,
                        ItemData[] outputs, UUID uuid, String craftingTag) {
        this(type, null, width, height, inputId, inputDamage, inputs, outputs, uuid, craftingTag, 0, -1);
    }

    public static CraftingData fromFurnaceData(int inputId, int inputDamage, ItemData output, String craftingTag,
                                               int networkId) {
        return new CraftingData(CraftingDataType.FURNACE_DATA, null, -1, -1, inputId, inputDamage,
                null, new ItemData[]{output}, null, craftingTag, -1, networkId);
    }

    public static CraftingData fromFurnaceData(int inputId, int inputDamage, ItemData output, String craftingTag) {
        return new CraftingData(CraftingDataType.FURNACE_DATA, null, -1, -1, inputId, inputDamage,
                null, new ItemData[]{output}, null, craftingTag, -1, -1);
    }

    public static CraftingData fromFurnace(int inputId, ItemData input, String craftingTag, int networkId) {
        return new CraftingData(CraftingDataType.FURNACE, null, -1, -1, inputId, -1,
                null, new ItemData[]{input}, null, craftingTag, -1, networkId);
    }

    public static CraftingData fromFurnace(int inputId, ItemData input, String craftingTag) {
        return new CraftingData(CraftingDataType.FURNACE, null, -1, -1, inputId, -1,
                null, new ItemData[]{input}, null, craftingTag, -1, -1);
    }

    public static CraftingData fromShapeless(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                             String craftingTag, int priority, int networkId) {
        return new CraftingData(CraftingDataType.SHAPELESS, recipeId, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    public static CraftingData fromShapeless(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                             String craftingTag, int priority) {
        return new CraftingData(CraftingDataType.SHAPELESS, recipeId, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, priority, -1);
    }

    public static CraftingData fromShaped(String recipeId, int width, int height, ItemData[] inputs,
                                          ItemData[] outputs, UUID uuid, String craftingTag, int priority,
                                          int networkId) {
        return new CraftingData(CraftingDataType.SHAPED, recipeId, width, height, -1, -1, inputs,
                outputs, uuid, craftingTag, priority, networkId);
    }

    public static CraftingData fromShaped(String recipeId, int width, int height, ItemData[] inputs,
                                          ItemData[] outputs, UUID uuid, String craftingTag, int priority) {
        return new CraftingData(CraftingDataType.SHAPED, recipeId, width, height, -1, -1, inputs,
                outputs, uuid, craftingTag, priority, -1);
    }

    public static CraftingData fromShapelessChemistry(String recipeId, ItemData[] inputs, ItemData[] outputs,
                                                      UUID uuid, String craftingTag, int priority, int networkId) {
        return new CraftingData(CraftingDataType.SHAPELESS_CHEMISTRY, recipeId, -1, -1, -1,
                -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    public static CraftingData fromShapelessChemistry(String recipeId, ItemData[] inputs, ItemData[] outputs,
                                                      UUID uuid, String craftingTag, int priority) {
        return new CraftingData(CraftingDataType.SHAPELESS_CHEMISTRY, recipeId, -1, -1, -1,
                -1, inputs, outputs, uuid, craftingTag, priority, -1);
    }

    public static CraftingData fromShapedChemistry(String recipeId, int width, int height, ItemData[] inputs,
                                                   ItemData[] outputs, UUID uuid, String craftingTag, int priority,
                                                   int networkId) {
        return new CraftingData(CraftingDataType.SHAPED_CHEMISTRY, recipeId, width, height, -1, -1,
                inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    public static CraftingData fromShapedChemistry(String recipeId, int width, int height, ItemData[] inputs,
                                                   ItemData[] outputs, UUID uuid, String craftingTag, int priority) {
        return new CraftingData(CraftingDataType.SHAPED_CHEMISTRY, recipeId, width, height, -1, -1,
                inputs, outputs, uuid, craftingTag, priority, -1);
    }

    public static CraftingData fromShulkerBox(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                              String craftingTag, int priority, int networkId) {
        return new CraftingData(CraftingDataType.SHULKER_BOX, recipeId, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    public static CraftingData fromShulkerBox(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                              String craftingTag, int priority) {
        return new CraftingData(CraftingDataType.SHULKER_BOX, recipeId, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, priority, -1);
    }

    public static CraftingData fromMulti(UUID uuid, int networkId) {
        return new CraftingData(CraftingDataType.MULTI, null, -1, -1, -1, -1,
                null, null, uuid, null, -1, networkId);
    }

    public static CraftingData fromMulti(UUID uuid) {
        return new CraftingData(CraftingDataType.MULTI, null, -1, -1, -1, -1,
                null, null, uuid, null, -1, -1);
    }

    public static CraftingData fromShapeless(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag,
                                             int networkId) {
        return new CraftingData(CraftingDataType.SHAPELESS, "", -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag, 0, networkId);
    }

    public static CraftingData fromShapeless(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag) {
        return new CraftingData(CraftingDataType.SHAPELESS, "", -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag, 0, -1);
    }

    public static CraftingData fromShaped(int width, int height, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                          String craftingTag, int networkId) {
        return new CraftingData(CraftingDataType.SHAPED, "", width, height, -1, -1, inputs, outputs, uuid,
                craftingTag, 0, networkId);
    }

    public static CraftingData fromShaped(int width, int height, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                          String craftingTag) {
        return new CraftingData(CraftingDataType.SHAPED, "", width, height, -1, -1, inputs, outputs, uuid,
                craftingTag, 0, -1);
    }

    public static CraftingData fromShapelessChemistry(ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                                      String craftingTag, int networkId) {
        return new CraftingData(CraftingDataType.SHAPELESS_CHEMISTRY, "", -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, 0, networkId);
    }

    public static CraftingData fromShapelessChemistry(ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                                      String craftingTag) {
        return new CraftingData(CraftingDataType.SHAPELESS_CHEMISTRY, "", -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag, 0, -1);
    }

    public static CraftingData fromShapedChemistry(int width, int height, ItemData[] inputs, ItemData[] outputs,
                                                   UUID uuid, String craftingTag, int networkId) {
        return new CraftingData(CraftingDataType.SHAPED_CHEMISTRY, "", width, height, -1, -1, inputs,
                outputs, uuid, craftingTag, 0, networkId);
    }

    public static CraftingData fromShapedChemistry(int width, int height, ItemData[] inputs, ItemData[] outputs,
                                                   UUID uuid, String craftingTag) {
        return new CraftingData(CraftingDataType.SHAPED_CHEMISTRY, "", width, height, -1, -1, inputs,
                outputs, uuid, craftingTag, 0, -1);
    }

    public static CraftingData fromShulkerBox(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag,
                                              int networkId) {
        return new CraftingData(CraftingDataType.SHULKER_BOX, "", -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag, 0, networkId);
    }

    public static CraftingData fromShulkerBox(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag) {
        return new CraftingData(CraftingDataType.SHULKER_BOX, "", -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag, 0, -1);
    }
}
