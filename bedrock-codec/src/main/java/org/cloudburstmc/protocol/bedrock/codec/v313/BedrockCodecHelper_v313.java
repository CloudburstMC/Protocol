package org.cloudburstmc.protocol.bedrock.codec.v313;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.BedrockCodecHelper_v291;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventorySource;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v313 extends BedrockCodecHelper_v291 {

    public BedrockCodecHelper_v313(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes) {
        super(entityData, gameRulesTypes);
    }

    @Override
    public InventorySource readSource(ByteBuf buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer.duplicate()));

        if (type == InventorySource.Type.UNTRACKED_INTERACTION_UI) {
            return InventorySource.fromUntrackedInteractionUI(VarInts.readInt(buffer));
        }
        return super.readSource(buffer);
    }
}
