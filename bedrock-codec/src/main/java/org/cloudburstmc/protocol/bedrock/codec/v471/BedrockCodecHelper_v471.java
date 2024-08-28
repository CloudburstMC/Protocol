package org.cloudburstmc.protocol.bedrock.codec.v471;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v465.BedrockCodecHelper_v465;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftGrindstoneAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftLoomAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v471 extends BedrockCodecHelper_v465 {

    public BedrockCodecHelper_v471(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
    }

    @Override
    protected ItemStackRequestAction readRequestActionData(ByteBuf byteBuf, ItemStackRequestActionType type) {
        switch (type) {
            case CRAFT_REPAIR_AND_DISENCHANT:
                return new CraftGrindstoneAction(VarInts.readUnsignedInt(byteBuf), 0, VarInts.readInt(byteBuf));
            case CRAFT_LOOM:
                return new CraftLoomAction(this.readString(byteBuf), 0);
            default:
                return super.readRequestActionData(byteBuf, type);
        }
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, ItemStackRequestAction action) {
        switch (action.getType()) {
            case CRAFT_REPAIR_AND_DISENCHANT:
                CraftGrindstoneAction actionData = (CraftGrindstoneAction) action;
                VarInts.writeUnsignedInt(byteBuf, actionData.getRecipeNetworkId());
                VarInts.writeInt(byteBuf, actionData.getRepairCost());
                return;
            case CRAFT_LOOM:
                this.writeString(byteBuf, ((CraftLoomAction) action).getPatternId());
                return;
            default:
                super.writeRequestActionData(byteBuf, action);
        }
    }
}