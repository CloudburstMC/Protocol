package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.CraftingDataSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v388 extends CraftingDataSerializer_v361 {
    public static final CraftingDataSerializer_v388 INSTANCE = new CraftingDataSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getCraftingData(), this::writeEntry);
        // Changes start
        helper.writeArray(buffer, packet.getPotionMixData(), this::writePotionMixData);
        helper.writeArray(buffer, packet.getContainerMixData(), this::writeContainerMixData);
        // Changes end
        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getCraftingData(), this::readEntry);
        // Changes start
        helper.readArray(buffer, packet.getPotionMixData(), this::readPotionMixData);
        helper.readArray(buffer, packet.getContainerMixData(), this::readContainerMixData);
        // Changes end
        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected PotionMixData readPotionMixData(ByteBuf buffer, BedrockCodecHelper helper) {
        int fromPotionId = VarInts.readInt(buffer);
        int ingredient = VarInts.readInt(buffer);
        int toPotionId = VarInts.readInt(buffer);
        return new PotionMixData(fromPotionId, 0, ingredient, 0, toPotionId, 0);
    }

    protected void writePotionMixData(ByteBuf buffer, BedrockCodecHelper helper, PotionMixData potionMixData) {
        VarInts.writeInt(buffer, potionMixData.getInputId());
        VarInts.writeInt(buffer, potionMixData.getReagentId());
        VarInts.writeInt(buffer, potionMixData.getOutputId());
    }

    protected ContainerMixData readContainerMixData(ByteBuf buffer, BedrockCodecHelper helper) {
        int fromItemId = VarInts.readInt(buffer);
        int ingredient = VarInts.readInt(buffer);
        int toItemId = VarInts.readInt(buffer);
        return new ContainerMixData(fromItemId, ingredient, toItemId);
    }

    protected void writeContainerMixData(ByteBuf buffer, BedrockCodecHelper helper, ContainerMixData containerMixData) {
        VarInts.writeInt(buffer, containerMixData.getInputId());
        VarInts.writeInt(buffer, containerMixData.getReagentId());
        VarInts.writeInt(buffer, containerMixData.getOutputId());
    }
}
