package com.nukkitx.protocol.bedrock.v422.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeOptionalStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.v407.serializer.ItemStackRequestSerializer_v407;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ItemStackRequestSerializer_v422 extends ItemStackRequestSerializer_v407 {

    public static final ItemStackRequestSerializer_v422 INSTANCE = new ItemStackRequestSerializer_v422();

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, BedrockPacketHelper helper, BedrockSession session) {
        if (action.getType() == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            VarInts.writeInt(byteBuf, ((CraftRecipeOptionalStackRequestActionData) action).getRecipeId());
        } else {
            super.writeRequestActionData(byteBuf, action, helper, session);
        }
        helper.writeArray(byteBuf, action.getFilterStrings(), helper::writeVarIntAsciiString);
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockPacketHelper helper, BedrockSession session) {
        StackRequestActionData action;
        if (type == StackRequestActionType.CRAFT_RECIPE_OPTIONAL) {
            action = new CraftRecipeOptionalStackRequestActionData(VarInts.readInt(byteBuf));
        } else {
            action = super.readRequestActionData(byteBuf, type, helper, session);
        }
        List<String> filterStrings = new ArrayList<>();
        helper.readArray(byteBuf, filterStrings, (byteBuf1 -> {
            String string = helper.readString(byteBuf1);
            System.out.println(string);
            return string;
        }));
        action.setFilterStrings(filterStrings.toArray(new String[0]));
        return action;
    }
}
