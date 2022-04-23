package org.cloudburstmc.protocol.bedrock.codec.v471;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.v465.BedrockCodecHelper_v465;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftGrindstoneStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftLoomStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v471 extends BedrockCodecHelper_v465 {

    public BedrockCodecHelper_v471(TypeMap<EntityData> entityData, TypeMap<EntityData.Type> entityDataTypes, TypeMap<EntityFlag> entityFlags, TypeMap<Class<?>> gameRulesTypes, TypeMap<StackRequestActionType> stackRequestActionTypes) {
        super(entityData, entityDataTypes, entityFlags, gameRulesTypes, stackRequestActionTypes);
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type) {
        switch (type) {
            case CRAFT_REPAIR_AND_DISENCHANT:
                return new CraftGrindstoneStackRequestActionData(VarInts.readUnsignedInt(byteBuf), VarInts.readInt(byteBuf));
            case CRAFT_LOOM:
                return new CraftLoomStackRequestActionData(this.readString(byteBuf));
            default:
                return super.readRequestActionData(byteBuf, type);
        }
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action) {
        switch (action.getType()) {
            case CRAFT_REPAIR_AND_DISENCHANT:
                CraftGrindstoneStackRequestActionData actionData = (CraftGrindstoneStackRequestActionData) action;
                VarInts.writeUnsignedInt(byteBuf, actionData.getRecipeNetworkId());
                VarInts.writeInt(byteBuf, actionData.getRepairCost());
                return;
            case CRAFT_LOOM:
                this.writeString(byteBuf, ((CraftLoomStackRequestActionData) action).getPatternId());
                return;
            default:
                super.writeRequestActionData(byteBuf, action);
        }
    }
}