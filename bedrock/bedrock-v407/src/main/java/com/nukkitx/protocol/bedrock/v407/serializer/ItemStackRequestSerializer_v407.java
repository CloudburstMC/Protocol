package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.*;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackRequestSerializer_v407 implements BedrockPacketSerializer<ItemStackRequestPacket> {

    public static final ItemStackRequestSerializer_v407 INSTANCE = new ItemStackRequestSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet, BedrockSession session) {
        helper.writeArray(buffer, packet.getRequests(), (buf, requests) -> {
            VarInts.writeInt(buf, requests.getRequestId());

            helper.writeArray(buf, requests.getActions(), (byteBuf, action) -> {
                StackRequestActionType type = action.getType();
                byteBuf.writeByte(helper.getIdFromStackRequestActionType(type));
                writeRequestActionData(byteBuf, action, helper, session);
            });
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
            return new ItemStackRequestPacket.Request(requestId, actions.toArray(new StackRequestActionData[0]), new String[0]);
        });
    }

    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, BedrockPacketHelper helper, BedrockSession session) {
        switch (action.getType()) {
            case TAKE:
            case PLACE:
                byteBuf.writeByte(((TransferStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, helper, ((TransferStackRequestActionData) action).getSource());
                writeStackRequestSlotInfo(byteBuf, helper, ((TransferStackRequestActionData) action).getDestination());
                break;
            case SWAP:
                writeStackRequestSlotInfo(byteBuf, helper, ((SwapStackRequestActionData) action).getSource());
                writeStackRequestSlotInfo(byteBuf, helper, ((SwapStackRequestActionData) action).getDestination());
                break;
            case DROP:
                byteBuf.writeByte(((DropStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, helper, ((DropStackRequestActionData) action).getSource());
                byteBuf.writeBoolean(((DropStackRequestActionData) action).isRandomly());
                break;
            case DESTROY:
                byteBuf.writeByte(((DestroyStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, helper, ((DestroyStackRequestActionData) action).getSource());
                break;
            case CONSUME:
                byteBuf.writeByte(((ConsumeStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, helper, ((ConsumeStackRequestActionData) action).getSource());
                break;
            case CREATE:
                byteBuf.writeByte(((CreateStackRequestActionData) action).getSlot());
                break;
            case LAB_TABLE_COMBINE:
                break;
            case BEACON_PAYMENT:
                VarInts.writeInt(byteBuf, ((BeaconPaymentStackRequestActionData) action).getPrimaryEffect());
                VarInts.writeInt(byteBuf, ((BeaconPaymentStackRequestActionData) action).getSecondaryEffect());
                break;
            case CRAFT_RECIPE:
            case CRAFT_RECIPE_AUTO:
                VarInts.writeUnsignedInt(byteBuf, ((RecipeStackRequestActionData) action).getRecipeNetworkId());
                break;
            case CRAFT_CREATIVE:
                VarInts.writeUnsignedInt(byteBuf, ((CraftCreativeStackRequestActionData) action).getCreativeItemNetworkId());
                break;
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                break;
            case CRAFT_RESULTS_DEPRECATED:
                helper.writeArray(byteBuf, ((CraftResultsDeprecatedStackRequestActionData) action).getResultItems(), (buf2, item) -> helper.writeItem(buf2, item, session));
                byteBuf.writeByte(((CraftResultsDeprecatedStackRequestActionData) action).getTimesCrafted());
                break;
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + action.getType());
        }
    }

    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockPacketHelper helper, BedrockSession session) {
        switch (type) {
            case TAKE:
                return new TakeStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf, helper),
                        readStackRequestSlotInfo(byteBuf, helper)
                );
            case PLACE:
                return new PlaceStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf, helper),
                        readStackRequestSlotInfo(byteBuf, helper)
                );
            case SWAP:
                return new SwapStackRequestActionData(
                        readStackRequestSlotInfo(byteBuf, helper),
                        readStackRequestSlotInfo(byteBuf, helper)
                );
            case DROP:
                return new DropStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf, helper),
                        byteBuf.readBoolean()
                );
            case DESTROY:
                return new DestroyStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf, helper)
                );
            case CONSUME:
                return new ConsumeStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf, helper)
                );
            case CREATE:
                return new CreateStackRequestActionData(
                        byteBuf.readByte()
                );
            case LAB_TABLE_COMBINE:
                return new LabTableCombineRequestActionData();
            case BEACON_PAYMENT:
                return new BeaconPaymentStackRequestActionData(
                        VarInts.readInt(byteBuf),
                        VarInts.readInt(byteBuf)
                );
            case CRAFT_RECIPE:
                return new CraftRecipeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf)
                );
            case CRAFT_RECIPE_AUTO:
                return new AutoCraftRecipeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf)
                );
            case CRAFT_CREATIVE:
                return new CraftCreativeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf)
                );
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                return new CraftNonImplementedStackRequestActionData();
            case CRAFT_RESULTS_DEPRECATED:
                return new CraftResultsDeprecatedStackRequestActionData(
                        helper.readArray(byteBuf, new ItemData[0], buf2 -> helper.readItem(buf2, session)),
                        byteBuf.readByte()
                );
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + type);
        }
    }

    protected StackRequestSlotInfoData readStackRequestSlotInfo(ByteBuf buffer, BedrockPacketHelper helper) {
        return new StackRequestSlotInfoData(
                ContainerSlotType.values()[buffer.readByte()],
                buffer.readByte(),
                VarInts.readInt(buffer)
        );
    }

    protected void writeStackRequestSlotInfo(ByteBuf buffer, BedrockPacketHelper helper, StackRequestSlotInfoData data) {
        buffer.writeByte(data.getContainer().ordinal());
        buffer.writeByte(data.getSlot());
        VarInts.writeInt(buffer, data.getStackNetworkId());
    }
}
