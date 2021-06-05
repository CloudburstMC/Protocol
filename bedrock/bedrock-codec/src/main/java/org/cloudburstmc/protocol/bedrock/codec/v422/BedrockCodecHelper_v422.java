package org.cloudburstmc.protocol.bedrock.codec.v422;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.v419.BedrockCodecHelper_v419;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeOptionalStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class BedrockCodecHelper_v422 extends BedrockCodecHelper_v419 {
    public static final BedrockCodecHelper_v422 INSTANCE = new BedrockCodecHelper_v422();

    @Override
    protected void registerStackActionRequestTypes() {
        this.stackRequestActionTypes.put(0, StackRequestActionType.TAKE);
        this.stackRequestActionTypes.put(1, StackRequestActionType.PLACE);
        this.stackRequestActionTypes.put(2, StackRequestActionType.SWAP);
        this.stackRequestActionTypes.put(3, StackRequestActionType.DROP);
        this.stackRequestActionTypes.put(4, StackRequestActionType.DESTROY);
        this.stackRequestActionTypes.put(5, StackRequestActionType.CONSUME);
        this.stackRequestActionTypes.put(6, StackRequestActionType.CREATE);
        this.stackRequestActionTypes.put(7, StackRequestActionType.LAB_TABLE_COMBINE);
        this.stackRequestActionTypes.put(8, StackRequestActionType.BEACON_PAYMENT);
        this.stackRequestActionTypes.put(9, StackRequestActionType.CRAFT_RECIPE);
        this.stackRequestActionTypes.put(10, StackRequestActionType.CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(11, StackRequestActionType.CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(12, StackRequestActionType.CRAFT_RECIPE_OPTIONAL); // new for v422
        this.stackRequestActionTypes.put(13, StackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(14, StackRequestActionType.CRAFT_RESULTS_DEPRECATED);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = this.getStackRequestActionTypeFromId(byteBuf.readByte());
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
            byteBuf.writeByte(this.getIdFromStackRequestActionType(type));
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
