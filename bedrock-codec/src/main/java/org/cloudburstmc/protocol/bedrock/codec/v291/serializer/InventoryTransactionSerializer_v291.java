package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryTransactionType;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v291 implements BedrockPacketSerializer<InventoryTransactionPacket> {
    public static final InventoryTransactionSerializer_v291 INSTANCE = new InventoryTransactionSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        InventoryTransactionType transactionType = packet.getTransactionType();
        VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

        helper.writeInventoryActions(buffer, packet.getActions(), false);

        switch (transactionType) {
            case ITEM_USE:
                helper.writeItemUse(buffer, packet);
                break;
            case ITEM_USE_ON_ENTITY:
                this.writeItemUseOnEntity(buffer, helper, packet);
                break;
            case ITEM_RELEASE:
                this.writeItemRelease(buffer, helper, packet);
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        InventoryTransactionType transactionType = InventoryTransactionType.values()[VarInts.readUnsignedInt(buffer)];
        packet.setTransactionType(transactionType);

        helper.readInventoryActions(buffer, packet.getActions());

        switch (transactionType) {
            case ITEM_USE:
                helper.readItemUse(buffer, packet);
                break;
            case ITEM_USE_ON_ENTITY:
                this.readItemUseOnEntity(buffer, helper, packet);
                break;
            case ITEM_RELEASE:
                this.readItemRelease(buffer, helper, packet);
                break;
        }
    }

    public void readItemUseOnEntity(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(helper.readItem(buffer));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setClickPosition(helper.readVector3f(buffer));
    }

    public void writeItemUseOnEntity(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getActionType());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        helper.writeItem(buffer, packet.getItemInHand());
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector3f(buffer, packet.getClickPosition());
    }

    public void readItemRelease(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(helper.readItem(buffer));
        packet.setHeadPosition(helper.readVector3f(buffer));
    }

    public void writeItemRelease(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getActionType());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        helper.writeItem(buffer, packet.getItemInHand());
        helper.writeVector3f(buffer, packet.getHeadPosition());
    }
}
