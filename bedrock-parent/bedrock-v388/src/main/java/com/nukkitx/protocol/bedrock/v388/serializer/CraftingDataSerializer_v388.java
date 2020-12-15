package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerMixData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.PotionMixData;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.v361.serializer.CraftingDataSerializer_v361;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v388 extends CraftingDataSerializer_v361 {
    public static final CraftingDataSerializer_v388 INSTANCE = new CraftingDataSerializer_v388();

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
