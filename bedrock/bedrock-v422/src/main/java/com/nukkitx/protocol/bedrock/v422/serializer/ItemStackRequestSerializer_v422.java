package com.nukkitx.protocol.bedrock.v422.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeOptionalStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import com.nukkitx.protocol.bedrock.v407.serializer.ItemStackRequestSerializer_v407;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ItemStackRequestSerializer_v422 extends ItemStackRequestSerializer_v407 {

    public static final ItemStackRequestSerializer_v422 INSTANCE = new ItemStackRequestSerializer_v422();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet, BedrockSession session) {
        helper.writeArray(buffer, packet.getRequests(), (buf, requests) -> {
            VarInts.writeInt(buf, requests.getRequestId());

            helper.writeArray(buf, requests.getActions(), (byteBuf, action) -> {
                StackRequestActionType type = action.getType();
                byteBuf.writeByte(helper.getIdFromStackRequestActionType(type));
                writeRequestActionData(byteBuf, action, type, helper, session);
            });
            helper.writeArray(buffer, requests.getFilterStrings(), helper::writeString);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet, BedrockSession session) {
        helper.readArray(buffer, packet.getRequests(), buf -> {
            int requestId = VarInts.readInt(buf);
            List<StackRequestActionData> actions = new ArrayList<>();

            helper.readArray(buf, actions, byteBuf -> {
                StackRequestActionType type = helper.getStackRequestActionTypeFromId(byteBuf.readByte());
                return readRequestActionData(byteBuf, type, helper, session);
            });
            List<String> filterStrings = new ArrayList<>();
            helper.readArray(buffer, filterStrings, helper::readString);
            return new ItemStackRequestPacket.Request(requestId, actions.toArray(new StackRequestActionData[0]), filterStrings.toArray(new String[0]));
        });
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, StackRequestActionType type, BedrockPacketHelper helper, BedrockSession session) {
        if (type == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            VarInts.writeInt(byteBuf, ((CraftRecipeOptionalStackRequestActionData) action).getRecipeId());
        } else {
            super.writeRequestActionData(byteBuf, action, type, helper, session);
        }
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockPacketHelper helper, BedrockSession session) {
        if (type == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            return new CraftRecipeOptionalStackRequestActionData(VarInts.readInt(byteBuf));
        }
        return super.readRequestActionData(byteBuf, type, helper, session);
    }
}
