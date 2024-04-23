package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v291 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v291 INSTANCE = new CraftingDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getCraftingData(), this::writeEntry);
        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getCraftingData(), this::readEntry);
        packet.setCleanRecipes(buffer.readBoolean());
    }

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
            case FURNACE_DATA:
                return this.readFurnaceRecipe(buffer, helper, type);
            case MULTI:
                return this.readMultiRecipe(buffer, helper, type);
            default:
                throw new IllegalArgumentException("Unhandled crafting data type: " + type);
        }
    }

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
            case FURNACE_DATA:
                this.writeFurnaceRecipe(buffer, helper, (FurnaceRecipeData) craftingData);
                break;
            case MULTI:
                this.writeMultiRecipe(buffer, helper, (MultiRecipeData) craftingData);
                break;
        }
    }

    protected ShapelessRecipeData readShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, buf -> ItemDescriptorWithCount.fromItem(helper.readItem(buf)));

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItem);

        UUID uuid = helper.readUuid(buffer);
        return ShapelessRecipeData.of(type, "", inputs, outputs, uuid, "", 0, -1);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, ShapelessRecipeData data) {
        helper.writeArray(buffer, data.getIngredients(), (buf, item) -> helper.writeItem(buf, item.toItem()));
        helper.writeArray(buffer, data.getResults(), helper::writeItem);
        helper.writeUuid(buffer, data.getUuid());
    }

    protected ShapedRecipeData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        for (int i = 0; i < inputCount; i++) {
            inputs.add(ItemDescriptorWithCount.fromItem(helper.readItem(buffer)));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItem);
        UUID uuid = helper.readUuid(buffer);
        return ShapedRecipeData.of(type, "", width, height, inputs, outputs, uuid, "", 0, -1);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, ShapedRecipeData data) {
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemDescriptorWithCount> inputs = data.getIngredients();
        for (int i = 0; i < count; i++) {
            helper.writeItem(buffer, inputs.get(i).toItem());
        }
        helper.writeArray(buffer, data.getResults(), helper::writeItem);
        helper.writeUuid(buffer, data.getUuid());
    }

    protected FurnaceRecipeData readFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
        ItemData result = helper.readItem(buffer);
        return FurnaceRecipeData.of(type, inputId, inputDamage, result, "");
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, FurnaceRecipeData data) {
        VarInts.writeInt(buffer, data.getInputId());
        if (data.getType() == CraftingDataType.FURNACE_DATA) {
            VarInts.writeInt(buffer, data.getInputData());
        }
        helper.writeItem(buffer, data.getResult());
    }

    protected MultiRecipeData readMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        return MultiRecipeData.of(uuid, -1);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, MultiRecipeData data) {
        helper.writeUuid(buffer, data.getUuid());
    }
}
