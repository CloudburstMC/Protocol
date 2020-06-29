package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v291 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v291 INSTANCE = new CraftingDataSerializer_v291();

    protected static final ItemData[] EMPTY_ARRAY = {};

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    this.writeShapelessRecipe(buf, helper, craftingData);
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    this.writeShapedRecipe(buf, helper, craftingData);
                    break;
                case FURNACE:
                case FURNACE_DATA:
                    this.writeFurnaceRecipe(buf, helper, craftingData);
                    break;
                case MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });
        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getCraftingData(), buf -> {
            int typeInt = VarInts.readInt(buf);
            CraftingDataType type = CraftingDataType.byId(typeInt);

            switch (type) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    return this.readShapelessRecipe(buf, helper, type);
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    return this.readShapedRecipe(buf, helper, type);
                case FURNACE:
                case FURNACE_DATA:
                    return this.readFurnaceRecipe(buf, helper, type);
                case MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });
        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        ItemData[] inputs = helper.readArray(buffer, EMPTY_ARRAY, helper::readItem);
        ItemData[] outputs = helper.readArray(buffer, EMPTY_ARRAY, helper::readItem);
        UUID uuid = helper.readUuid(buffer);
        return new CraftingData(type, -1, -1, -1, -1, inputs, outputs, uuid, null);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        helper.writeArray(buffer, data.getInputs(), helper::writeItem);
        helper.writeArray(buffer, data.getOutputs(), helper::writeItem);
        helper.writeUuid(buffer, data.getUuid());
    }

    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        ItemData[] inputs = new ItemData[inputCount];
        for (int i = 0; i < inputCount; i++) {
            inputs[i] = helper.readItem(buffer);
        }
        ItemData[] outputs = helper.readArray(buffer, EMPTY_ARRAY, helper::readItem);
        UUID uuid = helper.readUuid(buffer);
        return new CraftingData(type, width, height, -1, -1, inputs, outputs, uuid, null);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        ItemData[] inputs = data.getInputs();
        for (int i = 0; i < count; i++) {
            helper.writeItem(buffer, inputs[i]);
        }
        helper.writeArray(buffer, data.getOutputs(), helper::writeItem);
        helper.writeUuid(buffer, data.getUuid());
    }

    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
        ItemData[] output = new ItemData[]{helper.readItem(buffer)};
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                null, null);
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        VarInts.writeInt(buffer, data.getInputId());
        if (data.getType() == CraftingDataType.FURNACE_DATA) {
            VarInts.writeInt(buffer, data.getInputDamage());
        }
        helper.writeItem(buffer, data.getOutputs()[0]);
    }

    protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        return CraftingData.fromMulti(uuid);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        helper.writeUuid(buffer, data.getUuid());
    }
}
