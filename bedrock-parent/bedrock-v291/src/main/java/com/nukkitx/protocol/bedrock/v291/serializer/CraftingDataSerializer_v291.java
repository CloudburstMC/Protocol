package com.nukkitx.protocol.bedrock.v291.serializer;

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
public class CraftingDataSerializer_v291 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v291 INSTANCE = new CraftingDataSerializer_v291();

    protected static final ItemData[] EMPTY_ARRAY = {};

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
                case FURNACE_DATA:
                    this.writeFurnaceRecipe(buf, helper, craftingData, session);
                    break;
                case MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });
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
                case FURNACE_DATA:
                    return this.readFurnaceRecipe(buf, helper, type, session);
                case MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });
        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        List<ItemData> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, buf -> helper.readItem(buf, session));

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));

        UUID uuid = helper.readUuid(buffer);
        return new CraftingData(type, -1, -1, -1, -1, inputs, outputs, uuid, null);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        helper.writeArray(buffer, data.getInputs(), (buf, item) -> helper.writeItem(buf, item, session));
        helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
        helper.writeUuid(buffer, data.getUuid());
    }

    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemData> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(helper.readItem(buffer, session));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));
        UUID uuid = helper.readUuid(buffer);
        return new CraftingData(type, width, height, -1, -1, inputs, outputs, uuid, null);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemData> inputs = data.getInputs();
        for (int i = 0; i < count; i++) {
            helper.writeItem(buffer, inputs.get(i), session);
        }
        helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
        helper.writeUuid(buffer, data.getUuid());
    }

    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItem(buffer, session)));
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                null, null);
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        VarInts.writeInt(buffer, data.getInputId());
        if (data.getType() == CraftingDataType.FURNACE_DATA) {
            VarInts.writeInt(buffer, data.getInputDamage());
        }
        helper.writeItem(buffer, data.getOutputs().get(0), session);
    }

    protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        return CraftingData.fromMulti(uuid);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        helper.writeUuid(buffer, data.getUuid());
    }
}
