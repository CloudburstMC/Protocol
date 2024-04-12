package org.cloudburstmc.protocol.bedrock.codec.v671.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v582.serializer.CraftingDataSerializer_v582;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapedRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;
import java.util.UUID;

public class CraftingDataSerializer_v671 extends CraftingDataSerializer_v582 {
    public static final CraftingDataSerializer_v671 INSTANCE = new CraftingDataSerializer_v671();

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
        int networkId = VarInts.readUnsignedInt(buffer);
        return ShapedRecipeData.of(type, recipeId, width, height, inputs, outputs, uuid, craftingTag, priority, networkId, assumeSymmetry);
    }
}
