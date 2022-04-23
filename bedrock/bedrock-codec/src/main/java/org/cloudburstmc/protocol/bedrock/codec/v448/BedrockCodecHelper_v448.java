package org.cloudburstmc.protocol.bedrock.codec.v448;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.v440.BedrockCodecHelper_v440;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.AutoCraftRecipeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v448 extends BedrockCodecHelper_v440 {
    public BedrockCodecHelper_v448(TypeMap<EntityData> entityData, TypeMap<EntityData.Type> entityDataTypes, TypeMap<EntityFlag> entityFlags, TypeMap<Class<?>> gameRulesTypes, TypeMap<StackRequestActionType> stackRequestActionTypes) {
        super(entityData, entityDataTypes, entityFlags, gameRulesTypes, stackRequestActionTypes);
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type) {
        if (type == StackRequestActionType.CRAFT_RECIPE_AUTO) {
            return new AutoCraftRecipeStackRequestActionData(
                    VarInts.readUnsignedInt(byteBuf), byteBuf.readByte()
            );
        } else {
            return super.readRequestActionData(byteBuf, type);
        }
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action) {
        super.writeRequestActionData(byteBuf, action);
        if (action.getType() == StackRequestActionType.CRAFT_RECIPE_AUTO) {
            byteBuf.writeByte(((AutoCraftRecipeStackRequestActionData) action).getTimesCrafted());
        }
    }
}
