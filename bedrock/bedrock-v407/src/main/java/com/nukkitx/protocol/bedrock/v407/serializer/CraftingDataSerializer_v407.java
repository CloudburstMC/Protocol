package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v407 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v407 INSTANCE = new CraftingDataSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
        helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    this.writeShapelessRecipe(buf, helper, craftingData, session);
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    this.writeShapedRecipe(buf, helper, craftingData, session);
                    break;
                case FURNACE:
                    this.writeFurnaceRecipe(buf, helper, craftingData, session);
                    break;
                case FURNACE_DATA:
                    this.writeFurnaceDataRecipe(buf, helper, craftingData, session);
                    break;
                case MULTI:
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
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
        helper.readArray(buffer, packet.getCraftingData(), buf -> {
            int typeInt = VarInts.readInt(buf);
            CraftingDataType type = CraftingDataType.byId(typeInt);

            switch (type) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    return this.readShapelessRecipe(buf, helper, type, session);
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    return this.readShapedRecipe(buf, helper, type, session);
                case FURNACE:
                    return this.readFurnaceRecipe(buf, helper, type, session);
                case FURNACE_DATA:
                    return this.readFurnaceDataRecipe(buf, helper, type, session);
                case MULTI:
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

    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        String recipeId = helper.readString(buffer);
        List<ItemData> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, helper::readRecipeIngredient);

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf, session));

        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId,-1, -1, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        helper.writeString(buffer, data.getRecipeId());
        helper.writeArray(buffer, data.getInputs(), helper::writeRecipeIngredient);
        helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItemInstance(buf, item, session));

        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemData> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(helper.readRecipeIngredient(buffer));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf, session));
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        helper.writeString(buffer, data.getRecipeId());
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemData> inputs = data.getInputs();
        for (int i = 0; i < count; i++) {
            helper.writeRecipeIngredient(buffer, inputs.get(i));
        }
        helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItemInstance(buf, item, session));
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        int inputId = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer, session)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, -1, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        VarInts.writeInt(buffer, data.getInputId());
        helper.writeItemInstance(buffer, data.getOutputs().get(0), session);
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readFurnaceDataRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer, session)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceDataRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputDamage());
        helper.writeItemInstance(buffer, data.getOutputs().get(0), session);
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return CraftingData.fromMulti(uuid, networkId);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        helper.writeUuid(buffer, data.getUuid());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }
}
