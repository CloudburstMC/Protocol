package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.CraftingDataSerializer_v388;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;
import java.util.UUID;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v407 extends CraftingDataSerializer_v388 {
    public static final CraftingDataSerializer_v407 INSTANCE = new CraftingDataSerializer_v407();

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
        }
    }

    @Override
    protected ShapelessRecipeData readShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, helper::readIngredient);

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);

        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return ShapelessRecipeData.of(type, recipeId, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    @Override
    protected void writeShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, ShapelessRecipeData data) {
        helper.writeString(buffer, data.getId());
        helper.writeArray(buffer, data.getIngredients(), helper::writeIngredient);
        helper.writeArray(buffer, data.getResults(), helper::writeItemInstance);

        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }

    @Override
    protected ShapedRecipeData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        for (int i = 0; i < inputCount; i++) {
            inputs.add(helper.readIngredient(buffer));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return ShapedRecipeData.of(type, recipeId, width, height, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    @Override
    protected void writeShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, ShapedRecipeData data) {
        helper.writeString(buffer, data.getId());
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemDescriptorWithCount> inputs = data.getIngredients();
        for (int i = 0; i < count; i++) {
            helper.writeIngredient(buffer, inputs.get(i));
        }
        helper.writeArray(buffer, data.getResults(), helper::writeItemInstance);
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }

    @Override
    protected FurnaceRecipeData readFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        ItemData result = helper.readItemInstance(buffer);
        String craftingTag = helper.readString(buffer);
        return FurnaceRecipeData.of(type, inputId, -1, result, craftingTag);
    }

    @Override
    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, FurnaceRecipeData data) {
        VarInts.writeInt(buffer, data.getInputId());
        helper.writeItemInstance(buffer, data.getResult());
        helper.writeString(buffer, data.getTag());
    }

    protected FurnaceRecipeData readFurnaceDataRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = VarInts.readInt(buffer);
        ItemData result = helper.readItemInstance(buffer);
        String craftingTag = helper.readString(buffer);
        return FurnaceRecipeData.of(type, inputId, inputDamage, result, craftingTag);
    }

    protected void writeFurnaceDataRecipe(ByteBuf buffer, BedrockCodecHelper helper, FurnaceRecipeData data) {
        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputData());
        helper.writeItemInstance(buffer, data.getResult());
        helper.writeString(buffer, data.getTag());
    }

    protected MultiRecipeData readMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return MultiRecipeData.of(uuid, networkId);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, MultiRecipeData data) {
        helper.writeUuid(buffer, data.getUuid());
        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }

    @Override
    protected PotionMixData readPotionMixData(ByteBuf buffer, BedrockCodecHelper helper) {
        return new PotionMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    @Override
    protected void writePotionMixData(ByteBuf buffer, BedrockCodecHelper helper, PotionMixData data) {
        checkNotNull(data, "data is null");

        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputMeta());
        VarInts.writeInt(buffer, data.getReagentId());
        VarInts.writeInt(buffer, data.getReagentMeta());
        VarInts.writeInt(buffer, data.getOutputId());
        VarInts.writeInt(buffer, data.getOutputMeta());
    }

    @Override
    protected ContainerMixData readContainerMixData(ByteBuf buffer, BedrockCodecHelper helper) {
        return new ContainerMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    @Override
    protected void writeContainerMixData(ByteBuf buffer, BedrockCodecHelper helper, ContainerMixData data) {
        checkNotNull(data, "data is null");

        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getReagentId());
        VarInts.writeInt(buffer, data.getOutputId());
    }
}
