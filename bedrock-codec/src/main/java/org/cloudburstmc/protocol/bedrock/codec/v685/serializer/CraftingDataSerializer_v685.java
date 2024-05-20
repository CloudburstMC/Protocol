package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.UUID;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v671.serializer.CraftingDataSerializer_v671;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.RecipeUnlockingRequirement;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.CraftingRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapedRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapelessRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CraftingDataSerializer_v685 extends CraftingDataSerializer_v671 {
    public static final CraftingDataSerializer_v685 INSTANCE = new CraftingDataSerializer_v685();

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
        if (type == CraftingDataType.SHAPELESS) {
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

        if (data.getType() == CraftingDataType.SHAPELESS) {
            this.writeRequirement(buffer, helper, data);
        }
        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }

    @Override
    protected ShapedRecipeData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(helper.readIngredient(buffer));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        boolean assumeSymmetry = buffer.readBoolean();

        RecipeUnlockingRequirement requirement = RecipeUnlockingRequirement.INVALID;
        if (type == CraftingDataType.SHAPED) {
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
        int count = data.getWidth() * data.getHeight();
        List<ItemDescriptorWithCount> inputs = data.getIngredients();
        for (int i = 0; i < count; i++) {
            helper.writeIngredient(buffer, inputs.get(i));
        }
        helper.writeArray(buffer, data.getResults(), helper::writeItemInstance);
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getTag());
        VarInts.writeInt(buffer, data.getPriority());
        buffer.writeBoolean(data.isAssumeSymetry());

        if (data.getType() == CraftingDataType.SHAPED) {
            this.writeRequirement(buffer, helper, data);
        }

        VarInts.writeUnsignedInt(buffer, data.getNetId());
    }

    protected RecipeUnlockingRequirement readRequirement(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        RecipeUnlockingRequirement requirement = new RecipeUnlockingRequirement(RecipeUnlockingRequirement.UnlockingContext.from(buffer.readByte()));
        if (requirement.getContext().equals(RecipeUnlockingRequirement.UnlockingContext.NONE)) {
            helper.readArray(buffer, requirement.getIngredients(), (buf, h) -> h.readIngredient(buf));
        }
        return requirement;
    }

    protected void writeRequirement(ByteBuf buffer, BedrockCodecHelper helper, CraftingRecipeData data) {
        buffer.writeByte(data.getRequirement().getContext().ordinal());
        if (data.getRequirement().getContext().equals(RecipeUnlockingRequirement.UnlockingContext.NONE)) {
            helper.writeArray(buffer, data.getRequirement().getIngredients(), (buf, h, ingredient) -> h.writeIngredient(buf, ingredient));
        }
    }
}