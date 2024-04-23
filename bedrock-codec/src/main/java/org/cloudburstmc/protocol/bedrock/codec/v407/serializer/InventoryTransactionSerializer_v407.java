package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.InventoryTransactionSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryTransactionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.LegacySetItemSlotData;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v407 extends InventoryTransactionSerializer_v291 {
    public static final InventoryTransactionSerializer_v407 INSTANCE = new InventoryTransactionSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        int legacyRequestId = packet.getLegacyRequestId();
        VarInts.writeInt(buffer, legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            helper.writeArray(buffer, packet.getLegacySlots(), (buf, packetHelper, data) -> {
                buf.writeByte(data.getContainerId());
                packetHelper.writeByteArray(buf, data.getSlots());
            });
        }

        InventoryTransactionType transactionType = packet.getTransactionType();
        VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

        helper.writeInventoryActions(buffer, packet.getActions(), packet.isUsingNetIds());

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
        int legacyRequestId = VarInts.readInt(buffer);
        packet.setLegacyRequestId(legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            helper.readArray(buffer, packet.getLegacySlots(), (buf, packetHelper) -> {
                byte containerId = buf.readByte();
                byte[] slots = packetHelper.readByteArray(buf, 89); // 89 seems to be the largest slot count
                return new LegacySetItemSlotData(containerId, slots);
            });
        }

        InventoryTransactionType transactionType = InventoryTransactionType.values()[VarInts.readUnsignedInt(buffer)];
        packet.setTransactionType(transactionType);

        packet.setUsingNetIds(helper.readInventoryActions(buffer, packet.getActions()));

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
}
