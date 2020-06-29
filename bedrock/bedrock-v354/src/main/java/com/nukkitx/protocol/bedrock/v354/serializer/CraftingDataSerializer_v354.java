package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.v291.serializer.CraftingDataSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v354 extends CraftingDataSerializer_v291 {
    public static final CraftingDataSerializer_v354 INSTANCE = new CraftingDataSerializer_v354();

    @Override
    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        ItemData[] inputs = helper.readArray(buffer, EMPTY_ARRAY, helper::readItem);
        ItemData[] outputs = helper.readArray(buffer, EMPTY_ARRAY, helper::readItem);
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, -1, -1, inputs, outputs, uuid,
                craftingTag);
    }

    @Override
    protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        super.writeShapelessRecipe(buffer, helper, data);

        helper.writeString(buffer, data.getCraftingTag());
    }

    @Override
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
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, width, height, -1, -1, inputs, outputs, uuid,
                craftingTag);
    }

    @Override
    protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        super.writeShapedRecipe(buffer, helper, data);

        helper.writeString(buffer, data.getCraftingTag());
    }

    @Override
    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
        ItemData[] output = new ItemData[]{helper.readItem(buffer)};
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output, null,
                craftingTag);
    }

    @Override
    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        super.writeFurnaceRecipe(buffer, helper, data);

        helper.writeString(buffer, data.getCraftingTag());
    }
}
