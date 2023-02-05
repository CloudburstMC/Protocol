package com.nukkitx.protocol.bedrock.v567.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.v554.serializer.CraftingDataSerializer_v554;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v567 extends CraftingDataSerializer_v554 {

    public static final CraftingDataSerializer_v567 INSTANCE = new CraftingDataSerializer_v567();

    @Override
    protected void writeEntry(ByteBuf buf, BedrockPacketHelper helper, BedrockSession session, CraftingData craftingData) {
        if (craftingData.getType() == CraftingDataType.SMITHING_TRANSFORM) {
            helper.writeString(buf, craftingData.getRecipeId());
            writeIngredient(buf, helper, craftingData.getInputDescriptors().get(0));
            writeIngredient(buf, helper, craftingData.getInputDescriptors().get(1));
            helper.writeItemInstance(buf, craftingData.getOutputs().get(0), session);
            helper.writeString(buf, craftingData.getCraftingTag());
            VarInts.writeUnsignedInt(buf, craftingData.getNetworkId());
            return;
        }
        super.writeEntry(buf, helper, session, craftingData);
    }

    @Override
    protected CraftingData readEntry(ByteBuf buf, BedrockPacketHelper helper, BedrockSession session, CraftingDataType type) {
        if (type == CraftingDataType.SMITHING_TRANSFORM) {
            return CraftingData.fromSmithingTransform(
                    helper.readString(buf),
                    readIngredient(buf, helper),
                    readIngredient(buf, helper),
                    helper.readItemInstance(buf, session),
                    helper.readString(buf),
                    VarInts.readUnsignedInt(buf)
            );
        }
        return super.readEntry(buf, helper, session, type);
    }
}
