package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.CraftingData;
import com.nukkitx.protocol.bedrock.data.CraftingType;
import com.nukkitx.protocol.bedrock.data.ItemData;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingDataSerializer_v361 implements PacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v361 INSTANCE = new CraftingDataSerializer_v361();

    private static final InternalLogger log = InternalLoggerFactory.getInstance(CraftingDataSerializer_v361.class);

    @Override
    public void serialize(ByteBuf buffer, CraftingDataPacket packet) {
        BedrockUtils.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    BedrockUtils.writeString(buf, craftingData.getRecipeId());
                    BedrockUtils.writeArray(buf, craftingData.getInputs(), BedrockUtils::writeRecipeIngredient);
                    BedrockUtils.writeArray(buf, craftingData.getOutputs(), BedrockUtils::writeItemData);
                    BedrockUtils.writeUuid(buf, craftingData.getUuid());
                    BedrockUtils.writeString(buf, craftingData.getCraftingTag());
                    VarInts.writeInt(buf, craftingData.getPriority());
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    BedrockUtils.writeString(buf, craftingData.getRecipeId());
                    VarInts.writeInt(buf, craftingData.getWidth());
                    VarInts.writeInt(buf, craftingData.getHeight());
                    int count = craftingData.getWidth() * craftingData.getHeight();
                    ItemData[] inputs = craftingData.getInputs();
                    for (int i = 0; i < count; i++) {
                        BedrockUtils.writeRecipeIngredient(buf, inputs[i]);
                    }
                    BedrockUtils.writeArray(buf, craftingData.getOutputs(), BedrockUtils::writeItemData);
                    BedrockUtils.writeUuid(buf, craftingData.getUuid());
                    BedrockUtils.writeString(buf, craftingData.getCraftingTag());
                    VarInts.writeInt(buf, craftingData.getPriority());
                    break;
                case FURNACE:
                case FURNACE_DATA:
                    VarInts.writeInt(buf, craftingData.getInputId());
                    if (craftingData.getType() == CraftingType.FURNACE_DATA) {
                        VarInts.writeInt(buf, craftingData.getInputDamage());
                    }
                    BedrockUtils.writeItemData(buf, craftingData.getOutputs()[0]);
                    BedrockUtils.writeString(buf, craftingData.getCraftingTag());
                    break;
                case MULTI:
                    BedrockUtils.writeUuid(buf, craftingData.getUuid());
                    break;
            }
        });
        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, CraftingDataPacket packet) {
        BedrockUtils.readArray(buffer, packet.getCraftingData(), buf -> {
            int typeInt = VarInts.readInt(buf);
            CraftingType type = CraftingType.byId(typeInt);
            if (type == null) {
                throw new IllegalArgumentException("Unknown crafting type: " + typeInt);
            }

            switch (type) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    String recipeId = BedrockUtils.readString(buf);
                    int inputCount = VarInts.readUnsignedInt(buf);
                    ItemData[] inputs = new ItemData[inputCount];
                    for (int i = 0; i < inputCount; i++) {
                        inputs[i] = BedrockUtils.readRecipeIngredient(buf);
                    }
                    int outputCount = VarInts.readUnsignedInt(buf);
                    ItemData[] outputs = new ItemData[outputCount];
                    for (int i = 0; i < outputCount; i++) {
                        outputs[i] = BedrockUtils.readItemData(buf);
                    }
                    UUID uuid = BedrockUtils.readUuid(buf);
                    String craftingTag = BedrockUtils.readString(buf);
                    int priority = VarInts.readInt(buf);
                    return new CraftingData(type, recipeId, -1, -1, -1, -1, inputs,
                            outputs, uuid, craftingTag, priority);
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    recipeId = BedrockUtils.readString(buf);
                    int width = VarInts.readInt(buf);
                    int height = VarInts.readInt(buf);
                    inputCount = width * height;
                    inputs = new ItemData[inputCount];
                    for (int i = 0; i < inputCount; i++) {
                        inputs[i] = BedrockUtils.readRecipeIngredient(buf);
                    }
                    outputCount = VarInts.readUnsignedInt(buf);
                    outputs = new ItemData[outputCount];
                    for (int i = 0; i < outputCount; i++) {
                        outputs[i] = BedrockUtils.readItemData(buf);
                    }
                    uuid = BedrockUtils.readUuid(buf);
                    craftingTag = BedrockUtils.readString(buf);
                    priority = VarInts.readInt(buf);
                    return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs,
                            uuid, craftingTag, priority);
                case FURNACE:
                case FURNACE_DATA:
                    int inputId = VarInts.readInt(buf);
                    int inputDamage = type == CraftingType.FURNACE_DATA ? VarInts.readInt(buf) : -1;
                    ItemData[] output = new ItemData[]{BedrockUtils.readItemData(buf)};
                    craftingTag = BedrockUtils.readString(buf);
                    return new CraftingData(type, null, -1, -1, inputId, inputDamage, null,
                            output, null, craftingTag, -1);
                case MULTI:
                    uuid = BedrockUtils.readUuid(buf);
                    return CraftingData.fromMulti(uuid);
            }
            throw new IllegalArgumentException("Unhandled crafting data type: " + type);
        });
        packet.setCleanRecipes(buffer.readBoolean());
    }
}
