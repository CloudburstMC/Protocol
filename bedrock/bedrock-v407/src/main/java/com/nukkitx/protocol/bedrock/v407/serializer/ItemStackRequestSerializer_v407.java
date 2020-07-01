package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.StackRequestActionType;
import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.AutoCraftRecipeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.BeaconPaymentStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.ConsumeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftCreativeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftNonImplementedStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CraftResultsDeprecatedStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.CreateStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.DestroyStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.DropStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.LabTableCombineRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.PlaceStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.SwapStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.TakeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.TransferStackRequestActionData;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackRequestSerializer_v407 implements BedrockPacketSerializer<ItemStackRequestPacket> {

    public static final ItemStackRequestSerializer_v407 INSTANCE = new ItemStackRequestSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet) {
        helper.writeArray(buffer, packet.getRequests(), (buf, requests) -> {
            VarInts.writeInt(buf, requests.getRequestId());

            helper.writeArray(buf, requests.getActions(), (byteBuf, action) -> {
                StackRequestActionType type = StackRequestActionType.byClass(action.getClass());
                byteBuf.writeByte(type.ordinal());

                switch (type) {
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
                    case CONSUME:
                        byteBuf.writeByte(((DestroyStackRequestActionData) action).getCount());
                        writeStackRequestSlotInfo(byteBuf, helper, ((DestroyStackRequestActionData) action).getSource());
                        break;
                    case CREATE:
                        byteBuf.writeByte(((CreateStackRequestActionData) action).getSlot());
                        break;
                    case BEACON_PAYMENT:
                        VarInts.writeInt(byteBuf, ((BeaconPaymentStackRequestActionData) action).getPrimaryEffect());
                        VarInts.writeInt(byteBuf, ((BeaconPaymentStackRequestActionData) action).getSecondaryEffect());
                        break;
                    case CRAFT_RECIPE:
                    case CRAFT_RECIPE_AUTO:
                        VarInts.writeInt(byteBuf, ((CraftRecipeStackRequestActionData) action).getRecipeNetworkId());
                        break;
                    case CRAFT_CREATIVE:
                        VarInts.writeInt(byteBuf, ((CraftCreativeStackRequestActionData) action).getCreativeItemNetworkId());
                        break;
                    case CRAFT_RESULTS_DEPRECATED:
                        helper.writeArray(byteBuf, ((CraftResultsDeprecatedStackRequestActionData) action).getResultItems(), helper::writeItem);
                        byteBuf.writeByte(((CraftResultsDeprecatedStackRequestActionData) action).getTimesCrafted());
                        break;
                }
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet) {
        helper.readArray(buffer, packet.getRequests(), buf -> {
            int requestId = VarInts.readInt(buf);
            List<StackRequestActionData> actions = new ArrayList<>();

            helper.readArray(buf, actions, byteBuf -> {
                StackRequestActionType type = StackRequestActionType.byId(byteBuf.readByte());

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
                                VarInts.readInt(byteBuf)
                        );
                    case CRAFT_RECIPE_AUTO:
                        return new AutoCraftRecipeStackRequestActionData(
                                VarInts.readInt(byteBuf)
                        );
                    case CRAFT_CREATIVE:
                        return new CraftCreativeStackRequestActionData(
                                VarInts.readInt(byteBuf)
                        );
                    case CRAFT_RESULTS_DEPRECATED:
                        return new CraftResultsDeprecatedStackRequestActionData(
                                helper.readArray(byteBuf, new ItemData[0], helper::readItem),
                                byteBuf.readByte()
                        );
                }

                // Fallback
                return new CraftNonImplementedStackRequestActionData();
            });
            return new ItemStackRequestPacket.Request(requestId, actions.toArray(new StackRequestActionData[0]));
        });
    }

    public StackRequestSlotInfoData readStackRequestSlotInfo(ByteBuf buffer, BedrockPacketHelper helper) {
        return new StackRequestSlotInfoData(
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer)
        );
    }

    public void writeStackRequestSlotInfo(ByteBuf buffer, BedrockPacketHelper helper, StackRequestSlotInfoData data) {
        buffer.writeByte(data.getContainerId());
        buffer.writeByte(data.getSlot());
        VarInts.writeInt(buffer, data.getStackNetworkId());
    }
}
