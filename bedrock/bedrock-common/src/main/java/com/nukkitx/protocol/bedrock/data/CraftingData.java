package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

import java.util.UUID;

@Value
public class CraftingData {
    private final CraftingType type;
    private final int width;
    private final int height;
    private final int inputId;
    private final int inputDamage;
    private final ItemData[] inputs;
    private final ItemData[] outputs;
    private final UUID uuid;
    private final String craftingTag;

    public static CraftingData fromFurnaceData(int inputId, int inputDamage, ItemData input, String craftingTag) {
        return new CraftingData(CraftingType.FURNACE_DATA, -1, -1, inputId, inputDamage,
                new ItemData[]{input}, null, null, craftingTag);
    }

    public static CraftingData fromFurnace(int inputId, ItemData input, String craftingTag) {
        return new CraftingData(CraftingType.FURNACE, -1, -1, inputId, -1,
                new ItemData[]{input}, null, null, craftingTag);
    }

    public static CraftingData fromShapeless(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag) {
        return new CraftingData(CraftingType.SHAPELESS, -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag);
    }

    public static CraftingData fromShaped(int width, int height, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                          String craftingTag) {
        return new CraftingData(CraftingType.SHAPED, width, height, -1, -1, inputs, outputs, uuid,
                craftingTag);
    }

    public static CraftingData fromShapelessChemistry(ItemData[] inputs, ItemData[] outputs, UUID uuid,
                                                      String craftingTag) {
        return new CraftingData(CraftingType.SHAPELESS_CHEMISTRY, -1, -1, -1, -1,
                inputs, outputs, uuid, craftingTag);
    }

    public static CraftingData fromShapedChemistry(int width, int height, ItemData[] inputs, ItemData[] outputs,
                                                   UUID uuid, String craftingTag) {
        return new CraftingData(CraftingType.SHAPED_CHEMISTRY, width, height, -1, -1, inputs,
                outputs, uuid, craftingTag);
    }

    public static CraftingData fromShulkerBox(ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag) {
        return new CraftingData(CraftingType.SHULKER_BOX, -1, -1, -1, -1, inputs, outputs,
                uuid, craftingTag);
    }

    public static CraftingData fromMulti(UUID uuid) {
        return new CraftingData(CraftingType.MULTI, -1, -1, -1, -1, null,
                null, uuid, null);
    }
}
