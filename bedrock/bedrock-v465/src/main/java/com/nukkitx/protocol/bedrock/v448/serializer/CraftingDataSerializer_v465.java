package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.MaterialReducer;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.v407.serializer.CraftingDataSerializer_v407;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v465 extends CraftingDataSerializer_v407 {

    public static final CraftingDataSerializer_v465 INSTANCE = new CraftingDataSerializer_v465();

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
        helper.writeArray(buffer, packet.getPotionMixData(), helper::writePotionRecipe);

        // Potion Container Change
        helper.writeArray(buffer, packet.getContainerMixData(), helper::writeContainerChangeRecipe);

        // Material Reducers
        helper.writeArray(buffer, packet.getMaterialReducers(), this::writeMaterialReducer);

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

        // Material Reducers
        helper.readArray(buffer, packet.getMaterialReducers(), this::readMaterialReducer);

        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected void writeMaterialReducer(ByteBuf buffer, BedrockPacketHelper helper, MaterialReducer reducer) {
        VarInts.writeInt(buffer, reducer.getInputId());
        helper.writeArray(buffer, reducer.getItemCounts().int2IntEntrySet(), (buf, entry) -> {
            VarInts.writeInt(buffer, entry.getIntKey());
            VarInts.writeInt(buffer, entry.getIntValue());
        });
    }

    protected MaterialReducer readMaterialReducer(ByteBuf buffer, BedrockPacketHelper helper) {
        int inputId = VarInts.readInt(buffer);
        Int2IntMap itemCounts = new Int2IntOpenHashMap();
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            itemCounts.put(VarInts.readInt(buffer), VarInts.readInt(buffer));
        }
        return new MaterialReducer(inputId, itemCounts);
    }
}
