package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.InventoryTransactionSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v340 extends InventoryTransactionSerializer_v291 {
    public static final InventoryTransactionSerializer_v340 INSTANCE = new InventoryTransactionSerializer_v340();

    @Override
    public void readItemUse(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet) {
        super.readItemUse(buffer, helper, packet);

        packet.setBlockRuntimeId(VarInts.readUnsignedInt(buffer));
    }

    @Override
    public void writeItemUse(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet) {
        super.writeItemUse(buffer, helper, packet);

        VarInts.writeUnsignedInt(buffer, packet.getBlockRuntimeId());
    }
}
