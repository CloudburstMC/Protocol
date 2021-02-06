package com.nukkitx.protocol.bedrock.v422;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackRequest;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeOptionalStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.v419.BedrockPacketHelper_v419;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.*;

public class BedrockPacketHelper_v422 extends BedrockPacketHelper_v419 {
    public static final BedrockPacketHelper_v422 INSTANCE = new BedrockPacketHelper_v422();

    @Override
    protected void registerStackActionRequestTypes() {
        this.stackRequestActionTypes.put(0, TAKE);
        this.stackRequestActionTypes.put(1, PLACE);
        this.stackRequestActionTypes.put(2, SWAP);
        this.stackRequestActionTypes.put(3, DROP);
        this.stackRequestActionTypes.put(4, DESTROY);
        this.stackRequestActionTypes.put(5, CONSUME);
        this.stackRequestActionTypes.put(6, CREATE);
        this.stackRequestActionTypes.put(7, LAB_TABLE_COMBINE);
        this.stackRequestActionTypes.put(8, BEACON_PAYMENT);
        this.stackRequestActionTypes.put(9, CRAFT_RECIPE);
        this.stackRequestActionTypes.put(10, CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(11, CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(12, CRAFT_RECIPE_OPTIONAL); // new for v422
        this.stackRequestActionTypes.put(13, CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(14, CRAFT_RESULTS_DEPRECATED);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer, BedrockSession session) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = this.getStackRequestActionTypeFromId(byteBuf.readByte());
            return readRequestActionData(byteBuf, type, session);
        });
        List<String> filteredStrings = new ArrayList<>(); // new for v422
        this.readArray(buffer, filteredStrings, this::readString);
        return new ItemStackRequest(requestId, actions.toArray(new StackRequestActionData[0]), filteredStrings.toArray(new String[0]));
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, BedrockSession session, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            StackRequestActionType type = action.getType();
            byteBuf.writeByte(this.getIdFromStackRequestActionType(type));
            writeRequestActionData(byteBuf, action, session);
        });
        this.writeArray(buffer, request.getFilterStrings(), this::writeString); // new for v422
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockSession session) {
        StackRequestActionData action;
        if (type == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            action = new CraftRecipeOptionalStackRequestActionData(VarInts.readUnsignedInt(byteBuf), byteBuf.readIntLE());
        } else {
            action = super.readRequestActionData(byteBuf, type, session);
        }
        return action;
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, BedrockSession session) {
        if (action.getType() == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            VarInts.writeUnsignedInt(byteBuf, ((CraftRecipeOptionalStackRequestActionData) action).getRecipeNetworkId());
            byteBuf.writeIntLE(((CraftRecipeOptionalStackRequestActionData) action).getFilteredStringIndex());
        } else {
            super.writeRequestActionData(byteBuf, action, session);
        }
    }
}
