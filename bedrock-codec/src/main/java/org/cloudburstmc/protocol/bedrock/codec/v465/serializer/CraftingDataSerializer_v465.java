package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.CraftingDataSerializer_v407;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.MaterialReducer;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v465 extends CraftingDataSerializer_v407 {
    public static final CraftingDataSerializer_v465 INSTANCE = new CraftingDataSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
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
                    this.writeFurnaceRecipe(buf, helper, craftingData);
                    break;
                case FURNACE_DATA:
                    this.writeFurnaceDataRecipe(buf, helper, craftingData);
                    break;
                case MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });

        // Potions
        helper.writeArray(buffer, packet.getPotionMixData(), this::writePotionRecipe);

        // Potion Container Change
        helper.writeArray(buffer, packet.getContainerMixData(), this::writeContainerMixRecipe);

        // Material Reducers
        helper.writeArray(buffer, packet.getMaterialReducers(), this::writeMaterialReducer);

        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
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
                    return this.readFurnaceRecipe(buf, helper, type);
                case FURNACE_DATA:
                    return this.readFurnaceDataRecipe(buf, helper, type);
                case MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });

        // Potions
        helper.readArray(buffer, packet.getPotionMixData(), this::readPotionRecipe);

        // Potion Container Change
        helper.readArray(buffer, packet.getContainerMixData(), this::readContainerMixRecipe);

        // Material Reducers
        helper.readArray(buffer, packet.getMaterialReducers(), this::readMaterialReducer);

        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected void writeMaterialReducer(ByteBuf buffer, BedrockCodecHelper helper, MaterialReducer reducer) {
        VarInts.writeInt(buffer, reducer.getInputId());
        helper.writeArray(buffer, reducer.getItemCounts().object2IntEntrySet(), (buf, entry) -> {
            VarInts.writeInt(buffer, entry.getKey().getRuntimeId());
            VarInts.writeInt(buffer, entry.getIntValue());
        });
    }

    protected MaterialReducer readMaterialReducer(ByteBuf buffer, BedrockCodecHelper helper) {
        int inputId = VarInts.readInt(buffer);
        Object2IntMap<ItemDefinition> definitions = new Object2IntOpenHashMap<>();
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            definitions.put(helper.getItemDefinitions().getDefinition(VarInts.readInt(buffer)), VarInts.readInt(buffer));
        }
        return new MaterialReducer(inputId, definitions);
    }
}
