package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v748.serializer.CraftingDataSerializer_v748;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.RecipeUnlockingRequirement;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.CraftingRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapedRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapelessRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;
import java.util.UUID;

import static org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType.*;

public class CraftingDataSerializer_v2168 extends CraftingDataSerializer_v748 {

    public static final CraftingDataSerializer_v2168 INSTANCE = new CraftingDataSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getShapedData(), this::writeShapedRecipe);
        helper.writeArray(buffer, packet.getShapelessData(), this::writeShapelessRecipe);
        helper.writeArray(buffer, packet.getMultiData(), this::writeMultiRecipe);
        helper.writeArray(buffer, packet.getShapelessUserData(), this::writeShapelessRecipe);
        helper.writeArray(buffer, packet.getShapelessChemistryData(), this::writeShapelessRecipe);
        helper.writeArray(buffer, packet.getShapedChemistryData(), this::writeShapedRecipe);
        helper.writeArray(buffer, packet.getSmithingTransformData(), this::writeSmithingTransformRecipe);
        helper.writeArray(buffer, packet.getSmithingTrimData(), this::writeSmithingTrimRecipe);
        helper.writeArray(buffer, packet.getPotionMixData(), this::writePotionMixData);
        helper.writeArray(buffer, packet.getContainerMixData(), this::writeContainerMixData);
        helper.writeArray(buffer, packet.getMaterialReducers(), this::writeMaterialReducer);
        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getShapedData(),  (buf, h) -> readShapedRecipe(buf, h, SHAPED));
        helper.readArray(buffer, packet.getShapelessData(),  (buf, h) -> readShapelessRecipe(buf, h, SHAPELESS));
        helper.readArray(buffer, packet.getMultiData(),  (buf, h) -> readMultiRecipe(buf, h, MULTI));
        helper.readArray(buffer, packet.getShapelessUserData(),  (buf, h) -> readShapelessRecipe(buf, h, SHULKER_BOX));
        helper.readArray(buffer, packet.getShapelessChemistryData(),  (buf, h) -> readShapelessRecipe(buf, h, SHAPELESS_CHEMISTRY));
        helper.readArray(buffer, packet.getShapedChemistryData(),  (buf, h) -> readShapedRecipe(buf, h, SHAPED_CHEMISTRY));
        helper.readArray(buffer, packet.getSmithingTransformData(),  (buf, h) -> readSmithingTransformRecipe(buf, h, SMITHING_TRANSFORM));
        helper.readArray(buffer, packet.getSmithingTrimData(), (buf, h) -> readSmithingTrimRecipe(buf, h, SMITHING_TRIM));
        helper.readArray(buffer, packet.getPotionMixData(), this::readPotionMixData);
        helper.readArray(buffer, packet.getContainerMixData(), this::readContainerMixData);
        helper.readArray(buffer, packet.getMaterialReducers(), this::readMaterialReducer);
        packet.setCleanRecipes(buffer.readBoolean());
    }

    @Override
    protected RecipeUnlockingRequirement readRequirement(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        RecipeUnlockingRequirement requirement = new RecipeUnlockingRequirement(RecipeUnlockingRequirement.UnlockingContext.from(VarInts.readInt(buffer))); // docs said signed? but is unsigned?
        if (buffer.readBoolean()) {
            helper.readArray(buffer, requirement.getIngredients(), (buf, h) -> h.readIngredient(buf));
        }
        return requirement;
    }

    @Override
    protected void writeRequirement(ByteBuf buffer, BedrockCodecHelper helper, CraftingRecipeData data) {
        VarInts.writeInt(buffer, data.getRequirement().getContext().ordinal());
        helper.writeOptional(buffer, requirement-> requirement.getContext().equals(RecipeUnlockingRequirement.UnlockingContext.NONE), data.getRequirement(), (bb, hh, rr) ->
                hh.writeArray(bb, rr.getIngredients(), (buf, h, ingredient) -> h.writeIngredient(buf, ingredient)));
    }

    @Override
    protected ShapedRecipeData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, helper::readIngredient);
        if (inputs.size() != width * height) {
            throw new IllegalStateException(inputs.size() + "!=" + (width * height));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        boolean assumeSymmetry = buffer.readBoolean();

        RecipeUnlockingRequirement requirement = RecipeUnlockingRequirement.INVALID;
        if (buffer.readBoolean()) {
            requirement = this.readRequirement(buffer, helper, type);
        }

        int networkId = VarInts.readUnsignedInt(buffer);
        return ShapedRecipeData.of(type, recipeId, width, height, inputs, outputs, uuid, craftingTag, priority, networkId, assumeSymmetry, requirement);
    }

    @Override
    protected void writeShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, ShapedRecipeData data) {
        helper.writeString(buffer, data.getId());
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        helper.writeArray(buffer, data.getIngredients(), helper::writeIngredient);
        helper.writeArray(buffer, data.getResults(), helper::writeItemInstance);
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getTag());
        VarInts.writeInt(buffer, data.getPriority());
        buffer.writeBoolean(data.isAssumeSymmetry());

        buffer.writeBoolean(data.getType() == CraftingDataType.SHAPED);
        if (data.getType() == CraftingDataType.SHAPED) {
            this.writeRequirement(buffer, helper, data);
        }

        VarInts.writeUnsignedInt(buffer, data.getNetId());
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

        RecipeUnlockingRequirement requirement = RecipeUnlockingRequirement.INVALID;
        if (buffer.readBoolean()) {
            requirement = this.readRequirement(buffer, helper, type);
        }

        int networkId = VarInts.readUnsignedInt(buffer);
        return ShapelessRecipeData.of(type, recipeId, inputs, outputs, uuid, craftingTag, priority, networkId, requirement);
    }

    @Override
    protected void writeShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, ShapelessRecipeData data) {
        helper.writeString(buffer, data.getId());
        helper.writeArray(buffer, data.getIngredients(), helper::writeIngredient);
        helper.writeArray(buffer, data.getResults(), helper::writeItemInstance);

        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getTag());
        VarInts.writeInt(buffer, data.getPriority());

        buffer.writeBoolean(data.getType() == CraftingDataType.SHAPELESS || data.getType() == CraftingDataType.SHULKER_BOX);
        if (data.getType() == CraftingDataType.SHAPELESS || data.getType() == CraftingDataType.SHULKER_BOX) {
            this.writeRequirement(buffer, helper, data);
        }

        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }
}
