package org.cloudburstmc.protocol.bedrock.codec.v422;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v419.BedrockCodecHelper_v419;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeOptionalStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class BedrockCodecHelper_v422 extends BedrockCodecHelper_v419 {

    public BedrockCodecHelper_v422(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<StackRequestActionType> stackRequestActionTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = this.stackRequestActionTypes.getType(byteBuf.readByte());
            return readRequestActionData(byteBuf, type);
        });
        List<String> filteredStrings = new ArrayList<>(); // new for v422
        this.readArray(buffer, filteredStrings, this::readString);
        return new ItemStackRequest(requestId, actions.toArray(new StackRequestActionData[0]), filteredStrings.toArray(new String[0]));
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            StackRequestActionType type = action.getType();
            byteBuf.writeByte(this.stackRequestActionTypes.getId(type));
            writeRequestActionData(byteBuf, action);
        });
        this.writeArray(buffer, request.getFilterStrings(), this::writeString); // new for v422
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type) {
        StackRequestActionData action;
        if (type == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            action = new CraftRecipeOptionalStackRequestActionData(VarInts.readUnsignedInt(byteBuf), byteBuf.readIntLE());
        } else {
            action = super.readRequestActionData(byteBuf, type);
        }
        return action;
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action) {
        if (action.getType() == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            VarInts.writeUnsignedInt(byteBuf, ((CraftRecipeOptionalStackRequestActionData) action).getRecipeNetworkId());
            byteBuf.writeIntLE(((CraftRecipeOptionalStackRequestActionData) action).getFilteredStringIndex());
        } else {
            super.writeRequestActionData(byteBuf, action);
        }
    }
}
