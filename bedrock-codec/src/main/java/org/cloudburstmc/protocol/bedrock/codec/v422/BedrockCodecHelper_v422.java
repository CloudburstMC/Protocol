package org.cloudburstmc.protocol.bedrock.codec.v422;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v419.BedrockCodecHelper_v419;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftRecipeOptionalAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class BedrockCodecHelper_v422 extends BedrockCodecHelper_v419 {

    public BedrockCodecHelper_v422(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        int requestId = VarInts.readInt(buffer);
        List<ItemStackRequestAction> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            ItemStackRequestActionType type = this.stackRequestActionTypes.getType(byteBuf.readByte());
            return readRequestActionData(byteBuf, type);
        }, 32);
        List<String> filteredStrings = new ArrayList<>(); // new for v422
        this.readArray(buffer, filteredStrings, this::readString);
        return new ItemStackRequest(requestId, actions.toArray(new ItemStackRequestAction[0]), filteredStrings.toArray(new String[0]));
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            ItemStackRequestActionType type = action.getType();
            byteBuf.writeByte(this.stackRequestActionTypes.getId(type));
            writeRequestActionData(byteBuf, action);
        });
        this.writeArray(buffer, request.getFilterStrings(), this::writeString); // new for v422
    }

    @Override
    protected ItemStackRequestAction readRequestActionData(ByteBuf byteBuf, ItemStackRequestActionType type) {
        ItemStackRequestAction action;
        if (type == ItemStackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            action = new CraftRecipeOptionalAction(VarInts.readUnsignedInt(byteBuf), byteBuf.readIntLE());
        } else {
            action = super.readRequestActionData(byteBuf, type);
        }
        return action;
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, ItemStackRequestAction action) {
        if (action.getType() == ItemStackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            VarInts.writeUnsignedInt(byteBuf, ((CraftRecipeOptionalAction) action).getRecipeNetworkId());
            byteBuf.writeIntLE(((CraftRecipeOptionalAction) action).getFilteredStringIndex());
        } else {
            super.writeRequestActionData(byteBuf, action);
        }
    }
}
