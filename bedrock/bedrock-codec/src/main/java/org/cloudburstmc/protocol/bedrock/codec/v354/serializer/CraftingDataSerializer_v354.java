package org.cloudburstmc.protocol.bedrock.codec.v354.serializer;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.CraftingDataSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingData;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v354 extends CraftingDataSerializer_v291 {
    public static final CraftingDataSerializer_v354 INSTANCE = new CraftingDataSerializer_v354();

    @Override
    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        List<ItemData> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, buf -> helper.readItem(buf));

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItem(buf));

        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, -1, -1, inputs, outputs, uuid, craftingTag);
    }

    @Override
    protected void writeShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        super.writeShapelessRecipe(buffer, helper, data);

        helper.writeString(buffer, data.getCraftingTag());
    }

    @Override
    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemData> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(helper.readItem(buffer));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItem(buf));
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, width, height, -1, -1, inputs, outputs, uuid, craftingTag);
    }

    @Override
    protected void writeShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        super.writeShapedRecipe(buffer, helper, data);

        helper.writeString(buffer, data.getCraftingTag());
    }

    @Override
    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItem(buffer)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output, null, craftingTag);
    }

    @Override
    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        super.writeFurnaceRecipe(buffer, helper, data);

        helper.writeString(buffer, data.getCraftingTag());
    }
}
