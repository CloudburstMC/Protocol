package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.CraftingData;
import com.nukkitx.protocol.bedrock.data.CraftingType;
import com.nukkitx.protocol.bedrock.data.ItemData;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingDataSerializer_v354 implements PacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v354 INSTANCE = new CraftingDataSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, CraftingDataPacket packet) {
        BedrockUtils.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    BedrockUtils.writeArray(buf, craftingData.getInputs(), BedrockUtils::writeItemData);
                    BedrockUtils.writeArray(buf, craftingData.getOutputs(), BedrockUtils::writeItemData);
                    BedrockUtils.writeUuid(buf, craftingData.getUuid());
                    BedrockUtils.writeString(buf, craftingData.getCraftingTag());
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    VarInts.writeInt(buf, craftingData.getWidth());
                    VarInts.writeInt(buf, craftingData.getHeight());
                    int count = craftingData.getWidth() * craftingData.getHeight();
                    ItemData[] inputs = craftingData.getInputs();
                    for (int i = 0; i < count; i++) {
                        BedrockUtils.writeItemData(buf, inputs[i]);
                    }
                    BedrockUtils.writeArray(buf, craftingData.getOutputs(), BedrockUtils::writeItemData);
                    BedrockUtils.writeUuid(buf, craftingData.getUuid());
                    BedrockUtils.writeString(buf, craftingData.getCraftingTag());
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
                // TODO: 23/04/19 log type
                return null;
            }

            switch (type) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    int inputCount = VarInts.readUnsignedInt(buf);
                    ItemData[] inputs = new ItemData[inputCount];
                    for (int i = 0; i < inputCount; i++) {
                        inputs[i] = BedrockUtils.readItemData(buf);
                    }
                    int outputCount = VarInts.readUnsignedInt(buf);
                    ItemData[] outputs = new ItemData[outputCount];
                    for (int i = 0; i < outputCount; i++) {
                        outputs[i] = BedrockUtils.readItemData(buf);
                    }
                    UUID uuid = BedrockUtils.readUuid(buf);
                    String craftingTag = BedrockUtils.readString(buf);
                    return new CraftingData(type, -1, -1, -1, -1, inputs, outputs,
                            uuid, craftingTag);
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    int width = VarInts.readInt(buf);
                    int height = VarInts.readInt(buf);
                    int inputCount2 = width * height;
                    ItemData[] inputs2 = new ItemData[inputCount2];
                    for (int i = 0; i < inputCount2; i++) {
                        inputs2[i] = BedrockUtils.readItemData(buf);
                    }
                    int outputCount2 = VarInts.readUnsignedInt(buf);
                    ItemData[] outputs2 = new ItemData[outputCount2];
                    for (int i = 0; i < outputCount2; i++) {
                        outputs2[i] = BedrockUtils.readItemData(buf);
                    }
                    UUID uuid2 = BedrockUtils.readUuid(buf);
                    String craftingTag2 = BedrockUtils.readString(buf);
                    return new CraftingData(type, width, height, -1, -1, inputs2, outputs2, uuid2,
                            craftingTag2);
                case FURNACE:
                case FURNACE_DATA:
                    int inputId = VarInts.readInt(buf);
                    int inputDamage = type == CraftingType.FURNACE_DATA ? VarInts.readInt(buf) : -1;
                    ItemData[] output = new ItemData[]{BedrockUtils.readItemData(buf)};
                    String craftingTag3 = BedrockUtils.readString(buf);
                    return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                            null, craftingTag3);
                case MULTI:
                    UUID uuid3 = BedrockUtils.readUuid(buf);
                    return CraftingData.fromMulti(uuid3);
            }
            throw new IllegalArgumentException("Unhandled crafting data type: " + type);
        });
        packet.setCleanRecipes(buffer.readBoolean());
    }
}
