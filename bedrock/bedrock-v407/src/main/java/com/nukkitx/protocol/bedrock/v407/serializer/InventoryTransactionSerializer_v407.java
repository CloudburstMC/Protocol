package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.inventory.LegacySetItemSlotData;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.v340.serializer.InventoryTransactionSerializer_v340;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v407 extends InventoryTransactionSerializer_v340 {
    public static final InventoryTransactionSerializer_v407 INSTANCE = new InventoryTransactionSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet) {
        int legacyRequestId = packet.getLegacyRequestId();
        VarInts.writeInt(buffer, legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            helper.writeArray(buffer, packet.getLegacySlots(), (buf, packetHelper, data) -> {
                buf.writeByte(data.getContainerId());
                packetHelper.writeByteArray(buf, data.getSlots());
            });
        }

        super.serialize(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet) {
        int legacyRequestId = VarInts.readInt(buffer);
        packet.setLegacyRequestId(legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            helper.readArray(buffer, packet.getLegacySlots(), (buf, packetHelper) -> {
                byte containerId = buf.readByte();
                byte[] slots = packetHelper.readByteArray(buf);
                return new LegacySetItemSlotData(containerId, slots);
            });
        }

        super.deserialize(buffer, helper, packet);
    }
}
