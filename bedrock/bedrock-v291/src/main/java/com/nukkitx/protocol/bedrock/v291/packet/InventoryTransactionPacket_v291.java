package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class InventoryTransactionPacket_v291 extends InventoryTransactionPacket {

    @Override
    public void encode(ByteBuf buffer) {
        transactionType = Type.values()[VarInts.readUnsignedInt(buffer)];

        BedrockUtils.writeArray(buffer, actions, BedrockUtils::writeInventoryAction);

        switch (transactionType) {
            case ITEM_USE:
                VarInts.writeUnsignedInt(buffer, actionType);
                BedrockUtils.writeBlockPosition(buffer, blockPosition);
                VarInts.writeInt(buffer, face);
                VarInts.writeInt(buffer, hotbarSlot);
                BedrockUtils.writeItemInstance(buffer, itemInHand);
                BedrockUtils.writeVector3f(buffer, playerPosition);
                BedrockUtils.writeVector3f(buffer, clickPosition);
                break;
            case ITEM_USE_ON_ENTITY:
                VarInts.writeUnsignedInt(buffer, runtimeEntityId);
                VarInts.writeUnsignedInt(buffer, actionType);
                VarInts.writeInt(buffer, hotbarSlot);
                BedrockUtils.writeItemInstance(buffer, itemInHand);
                BedrockUtils.writeVector3f(buffer, playerPosition);
                BedrockUtils.writeVector3f(buffer, clickPosition);
                break;
            case ITEM_RELEASE:
                VarInts.writeUnsignedInt(buffer, actionType);
                VarInts.writeInt(buffer, hotbarSlot);
                BedrockUtils.writeItemInstance(buffer, itemInHand);
                BedrockUtils.writeVector3f(buffer, headPosition);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        transactionType = Type.values()[VarInts.readUnsignedInt(buffer)];

        BedrockUtils.readArray(buffer, actions, BedrockUtils::readInventoryAction);

        switch (transactionType) {
            case ITEM_USE:
                actionType = VarInts.readUnsignedInt(buffer);
                blockPosition = BedrockUtils.readBlockPosition(buffer);
                face = VarInts.readInt(buffer);
                hotbarSlot = VarInts.readInt(buffer);
                itemInHand = BedrockUtils.readItemInstance(buffer);
                playerPosition = BedrockUtils.readVector3f(buffer);
                clickPosition = BedrockUtils.readVector3f(buffer);
                break;
            case ITEM_USE_ON_ENTITY:
                runtimeEntityId = VarInts.readUnsignedInt(buffer);
                actionType = VarInts.readUnsignedInt(buffer);
                hotbarSlot = VarInts.readInt(buffer);
                itemInHand = BedrockUtils.readItemInstance(buffer);
                playerPosition = BedrockUtils.readVector3f(buffer);
                clickPosition = BedrockUtils.readVector3f(buffer);
                break;
            case ITEM_RELEASE:
                actionType = VarInts.readUnsignedInt(buffer);
                hotbarSlot = VarInts.readInt(buffer);
                itemInHand = BedrockUtils.readItemInstance(buffer);
                headPosition = BedrockUtils.readVector3f(buffer);
        }
    }
}
