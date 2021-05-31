package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingData;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v407 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v407 INSTANCE = new CraftingDataSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case CraftingDataType.SHAPELESS:
                case CraftingDataType.SHAPELESS_CHEMISTRY:
                case CraftingDataType.SHULKER_BOX:
                    this.writeShapelessRecipe(buf, helper, craftingData);
                    break;
                case CraftingDataType.SHAPED:
                case CraftingDataType.SHAPED_CHEMISTRY:
                    this.writeShapedRecipe(buf, helper, craftingData);
                    break;
                case CraftingDataType.FURNACE:
                    this.writeFurnaceRecipe(buf, helper, craftingData);
                    break;
                case CraftingDataType.FURNACE_DATA:
                    this.writeFurnaceDataRecipe(buf, helper, craftingData);
                    break;
                case CraftingDataType.MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });

        // Potions
        helper.writeArray(buffer, packet.getPotionMixData(), (buf, data) -> helper.writePotionRecipe(buf, data));

        // Potion Container Change
        helper.writeArray(buffer, packet.getContainerMixData(), (buf, data) -> helper.writeContainerChangeRecipe(buf, data));

        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getCraftingData(), buf -> {
            int typeInt = VarInts.readInt(buf);
            CraftingDataType type = CraftingDataType.byId(typeInt);

            switch (type) {
                case CraftingDataType.SHAPELESS:
                case CraftingDataType.SHAPELESS_CHEMISTRY:
                case CraftingDataType.SHULKER_BOX:
                    return this.readShapelessRecipe(buf, helper, type);
                case CraftingDataType.SHAPED:
                case CraftingDataType.SHAPED_CHEMISTRY:
                    return this.readShapedRecipe(buf, helper, type);
                case CraftingDataType.FURNACE:
                    return this.readFurnaceRecipe(buf, helper, type);
                case CraftingDataType.FURNACE_DATA:
                    return this.readFurnaceDataRecipe(buf, helper, type);
                case CraftingDataType.MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });

        // Potions
        helper.readArray(buffer, packet.getPotionMixData(), helper::readPotionRecipe);

        // Potion Container Change
        helper.readArray(buffer, packet.getContainerMixData(), helper::readContainerChangeRecipe);

        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        List<ItemData> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, helper::readRecipeIngredient);

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf));

        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, -1, -1, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        helper.writeString(buffer, data.getRecipeId());
        helper.writeArray(buffer, data.getInputs(), helper::writeRecipeIngredient);
        helper.writeArray(buffer, data.getOutputs(), helper::writeItemInstance);

        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemData> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(helper.readRecipeIngredient(buffer));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        helper.writeString(buffer, data.getRecipeId());
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemData> inputs = data.getInputs();
        for (int i = 0; i < count; i++) {
            helper.writeRecipeIngredient(buffer, inputs.get(i));
        }
        helper.writeArray(buffer, data.getOutputs(), helper::writeItemInstance);
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, -1, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        VarInts.writeInt(buffer, data.getInputId());
        helper.writeItemInstance(buffer, data.getOutputs().get(0));
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readFurnaceDataRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceDataRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputDamage());
        helper.writeItemInstance(buffer, data.getOutputs().get(0));
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return CraftingData.fromMulti(uuid, networkId);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        helper.writeUuid(buffer, data.getUuid());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }
}
