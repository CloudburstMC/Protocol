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
                writeRequestActionData(byteBuf, action, helper, session);
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
            List<String> filteredStrings = new ArrayList<>();
            helper.readArray(buf, filteredStrings, helper::readString);
            return new ItemStackRequestPacket.Request(requestId, actions.toArray(new StackRequestActionData[0]), filteredStrings.toArray(new String[0]));
        });
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, BedrockPacketHelper helper, BedrockSession session) {
        if (action.getType() == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            VarInts.writeUnsignedInt(byteBuf, ((CraftRecipeOptionalStackRequestActionData) action).getRecipeNetworkId());
            byteBuf.writeIntLE(((CraftRecipeOptionalStackRequestActionData) action).getFilteredStringIndex());
        } else {
            super.writeRequestActionData(byteBuf, action, helper, session);
        }
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockPacketHelper helper, BedrockSession session) {
        StackRequestActionData action;
        if (type == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            action = new CraftRecipeOptionalStackRequestActionData(VarInts.readUnsignedInt(byteBuf), byteBuf.readIntLE());
        } else {
            action = super.readRequestActionData(byteBuf, type, helper, session);
        }
        return action;
    }
}
