package org.cloudburstmc.protocol.bedrock.codec.v705.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v662.serializer.PlayerAuthInputSerializer_v662;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;

public class PlayerAuthInputSerializer_v705 extends PlayerAuthInputSerializer_v662 {
    public static final PlayerAuthInputSerializer_v705 INSTANCE = new PlayerAuthInputSerializer_v705();

    @Override
    protected void writeItemUseTransaction(ByteBuf buffer, BedrockCodecHelper helper, ItemUseTransaction transaction) {
        super.writeItemUseTransaction(buffer, helper, transaction);
        buffer.writeByte(transaction.getClientInteractPrediction().ordinal());
    }

    @Override
    protected ItemUseTransaction readItemUseTransaction(ByteBuf buffer, BedrockCodecHelper helper) {
        ItemUseTransaction transaction = super.readItemUseTransaction(buffer, helper);
        transaction.setClientInteractPrediction(ItemUseTransaction.PredictedResult.values()[buffer.readByte()]);
        return transaction;
    }
}