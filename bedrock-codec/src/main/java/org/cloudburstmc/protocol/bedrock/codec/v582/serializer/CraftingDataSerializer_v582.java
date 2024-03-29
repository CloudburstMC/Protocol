package org.cloudburstmc.protocol.bedrock.codec.v582.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v567.serializer.CraftingDataSerializer_v567;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CraftingDataSerializer_v582 extends CraftingDataSerializer_v567 {

    @Override
    protected RecipeData readEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        int typeInt = VarInts.readInt(buffer);
        CraftingDataType type = CraftingDataType.byId(typeInt);

        switch (type) {
            case SHAPELESS:
            case SHAPELESS_CHEMISTRY:
            case SHULKER_BOX:
                return this.readShapelessRecipe(buffer, helper, type);
            case SHAPED:
            case SHAPED_CHEMISTRY:
                return this.readShapedRecipe(buffer, helper, type);
            case FURNACE:
                return this.readFurnaceRecipe(buffer, helper, type);
            case FURNACE_DATA:
                return this.readFurnaceDataRecipe(buffer, helper, type);
            case MULTI:
                return this.readMultiRecipe(buffer, helper, type);
            case SMITHING_TRANSFORM:
                return this.readSmithingTransformRecipe(buffer, helper, type);
            case SMITHING_TRIM:
                return this.readSmithingTrimRecipe(buffer, helper, type);
            default:
                throw new IllegalArgumentException("Unhandled crafting data type: " + type);
        }
    }

    @Override
    protected void writeEntry(ByteBuf buffer, BedrockCodecHelper helper, RecipeData craftingData) {
        VarInts.writeInt(buffer, craftingData.getType().ordinal());
        switch (craftingData.getType()) {
            case SHAPELESS:
            case SHAPELESS_CHEMISTRY:
            case SHULKER_BOX:
                this.writeShapelessRecipe(buffer, helper, (ShapelessRecipeData) craftingData);
                break;
            case SHAPED:
            case SHAPED_CHEMISTRY:
                this.writeShapedRecipe(buffer, helper, (ShapedRecipeData) craftingData);
                break;
            case FURNACE:
                this.writeFurnaceRecipe(buffer, helper, (FurnaceRecipeData) craftingData);
                break;
            case FURNACE_DATA:
                this.writeFurnaceDataRecipe(buffer, helper, (FurnaceRecipeData) craftingData);
                break;
            case MULTI:
                this.writeMultiRecipe(buffer, helper, (MultiRecipeData) craftingData);
                break;
            case SMITHING_TRANSFORM:
                this.writeSmithingTransformRecipe(buffer, helper, (SmithingTransformRecipeData) craftingData);
                break;
            case SMITHING_TRIM:
                this.writeSmithingTrimRecipe(buffer, helper, (SmithingTrimRecipeData) craftingData);
                break;
        }
    }

    protected SmithingTrimRecipeData readSmithingTrimRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String id = helper.readString(buffer);
        ItemDescriptorWithCount template = helper.readIngredient(buffer);
        ItemDescriptorWithCount base = helper.readIngredient(buffer);
        ItemDescriptorWithCount addition = helper.readIngredient(buffer);
        return SmithingTrimRecipeData.of(id, base, addition, template,
                helper.readString(buffer), // Tag
                VarInts.readUnsignedInt(buffer) // Net ID
        );
    }

    protected void writeSmithingTrimRecipe(ByteBuf buffer, BedrockCodecHelper helper, SmithingTrimRecipeData data) {
        helper.writeString(buffer, data.getId());
        helper.writeIngredient(buffer, data.getTemplate());
        helper.writeIngredient(buffer, data.getBase());
        helper.writeIngredient(buffer, data.getAddition());
        helper.writeString(buffer, data.getTag());
        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }

    @Override
    protected SmithingTransformRecipeData readSmithingTransformRecipe(ByteBuf buffer, BedrockCodecHelper helper,
                                                                      CraftingDataType type) {
        return SmithingTransformRecipeData.of(
                helper.readString(buffer), // ID
                helper.readIngredient(buffer), // Template - new for 1.19.80
                helper.readIngredient(buffer), // Base
                helper.readIngredient(buffer), // Addition
                helper.readItemInstance(buffer), // Result
                helper.readString(buffer), // Tag
                VarInts.readUnsignedInt(buffer) // Net ID
        );
    }

    @Override
    protected void writeSmithingTransformRecipe(ByteBuf buffer, BedrockCodecHelper helper,
                                                SmithingTransformRecipeData data) {
        helper.writeString(buffer, data.getId());
        helper.writeIngredient(buffer, data.getTemplate());
        helper.writeIngredient(buffer, data.getBase());
        helper.writeIngredient(buffer, data.getAddition());
        helper.writeItemInstance(buffer, data.getResult());
        helper.writeString(buffer, data.getTag());
        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }
}
