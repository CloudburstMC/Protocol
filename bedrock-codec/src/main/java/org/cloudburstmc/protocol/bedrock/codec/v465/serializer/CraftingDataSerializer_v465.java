package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.CraftingDataSerializer_v407;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.MaterialReducer;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v465 extends CraftingDataSerializer_v407 {
    public static final CraftingDataSerializer_v465 INSTANCE = new CraftingDataSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getCraftingData(), this::writeEntry);
        helper.writeArray(buffer, packet.getPotionMixData(), this::writePotionMixData);
        helper.writeArray(buffer, packet.getContainerMixData(), this::writeContainerMixData);

        helper.writeArray(buffer, packet.getMaterialReducers(), this::writeMaterialReducer); // Addition

        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getCraftingData(), this::readEntry);
        helper.readArray(buffer, packet.getPotionMixData(), this::readPotionMixData);
        helper.readArray(buffer, packet.getContainerMixData(), this::readContainerMixData);

        helper.readArray(buffer, packet.getMaterialReducers(), this::readMaterialReducer); // Addition

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
