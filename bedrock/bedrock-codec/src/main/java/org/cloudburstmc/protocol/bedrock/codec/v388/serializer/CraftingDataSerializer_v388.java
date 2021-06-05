package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.CraftingDataSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.PotionMixData;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v388 extends CraftingDataSerializer_v361 {
    public static final CraftingDataSerializer_v388 INSTANCE = new CraftingDataSerializer_v388();

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
                case CraftingDataType.FURNACE_DATA:
                    this.writeFurnaceRecipe(buf, helper, craftingData);
                    break;
                case CraftingDataType.MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });

        helper.writeArray(buffer, packet.getPotionMixData(), (buf, potionMixData) -> {
            VarInts.writeInt(buf, potionMixData.getInputId());
            VarInts.writeInt(buf, potionMixData.getReagentId());
            VarInts.writeInt(buf, potionMixData.getOutputId());
        });
        helper.writeArray(buffer, packet.getContainerMixData(), (buf, containerMixData) -> {
            VarInts.writeInt(buf, containerMixData.getInputId());
            VarInts.writeInt(buf, containerMixData.getReagentId());
            VarInts.writeInt(buf, containerMixData.getOutputId());
        });

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
                case CraftingDataType.FURNACE_DATA:
                    return this.readFurnaceRecipe(buf, helper, type);
                case CraftingDataType.MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });
        helper.readArray(buffer, packet.getPotionMixData(), buf -> {
            int fromPotionId = VarInts.readInt(buf);
            int ingredient = VarInts.readInt(buf);
            int toPotionId = VarInts.readInt(buf);
            return new PotionMixData(fromPotionId, 0, ingredient, 0, toPotionId, 0);
        });
        helper.readArray(buffer, packet.getContainerMixData(), buf -> {
            int fromItemId = VarInts.readInt(buf);
            int ingredient = VarInts.readInt(buf);
            int toItemId = VarInts.readInt(buf);
            return new ContainerMixData(fromItemId, ingredient, toItemId);
        });
        packet.setCleanRecipes(buffer.readBoolean());
    }
}
