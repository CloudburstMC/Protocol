package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingDataSerializer_v388 implements PacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v388 INSTANCE = new CraftingDataSerializer_v388();

    private static final InternalLogger log = InternalLoggerFactory.getInstance(CraftingDataSerializer_v388.class);

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
        BedrockUtils.writeArray(buffer, packet.getPotionMixData(), (buf, potionMixData) -> {
            VarInts.writeInt(buf, potionMixData.getFromPotionId());
            VarInts.writeInt(buf, potionMixData.getIngredient());
            VarInts.writeInt(buf, potionMixData.getToPotionId());
        });
        BedrockUtils.writeArray(buffer, packet.getContainerMixData(), (buf, containerMixData) -> {
            VarInts.writeInt(buf, containerMixData.getFromItemId());
            VarInts.writeInt(buf, containerMixData.getIngredient());
            VarInts.writeInt(buf, containerMixData.getToItemId());
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
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });
        BedrockUtils.readArray(buffer, packet.getPotionMixData(), buf -> {
            int fromPotionId = VarInts.readInt(buf);
            int ingredient = VarInts.readInt(buf);
            int toPotionId = VarInts.readInt(buf);
            return new PotionMixData(fromPotionId, ingredient, toPotionId);
        });
        BedrockUtils.readArray(buffer, packet.getContainerMixData(), buf -> {
            int fromItemId = VarInts.readInt(buf);
            int ingredient = VarInts.readInt(buf);
            int toItemId = VarInts.readInt(buf);
            return new ContainerMixData(fromItemId, ingredient, toItemId);
        });
        packet.setCleanRecipes(buffer.readBoolean());
    }
}
