package org.cloudburstmc.protocol.bedrock.codec.v313;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.v291.BedrockCodecHelper_v291;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventorySource;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v313 extends BedrockCodecHelper_v291 {

    public BedrockCodecHelper_v313(TypeMap<EntityData> entityData, TypeMap<EntityData.Type> entityDataTypes,
                                   TypeMap<EntityFlag> entityFlags, TypeMap<Class<?>> gameRulesTypes) {
        super(entityData, entityDataTypes, entityFlags, gameRulesTypes);
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
