package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.v291.serializer.InventoryTransactionSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v313 extends InventoryTransactionSerializer_v291 {
    public static final InventoryTransactionSerializer_v313 INSTANCE = new InventoryTransactionSerializer_v313();


    @Override
    public InventorySource readSource(ByteBuf buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer.duplicate()));

        if (type == InventorySource.Type.UNTRACKED_INTERACTION_UI) {
            return InventorySource.fromUntrackedInteractionUI(VarInts.readInt(buffer));
        }
        return super.readSource(buffer);
    }
}
