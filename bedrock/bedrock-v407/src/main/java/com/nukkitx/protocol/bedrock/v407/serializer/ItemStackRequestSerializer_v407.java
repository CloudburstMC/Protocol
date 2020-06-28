package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackActionDeprecated;
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
                switch (action.getType()) {
                    case 0:
                    case 1:
                    case 2:
                        byteBuf.writeByte(action.getBaseByte0());
                        byteBuf.writeByte(action.getBaseByte1());
                        byteBuf.writeByte(action.getBaseByte2());
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        byteBuf.writeByte(action.getFlagsByte0());
                        byteBuf.writeByte(action.getFlagsByte1());
                        VarInts.writeInt(byteBuf, action.getFlagsVarInt0());
                        break;
                    case 3:
                        byteBuf.writeByte(action.getBaseByte0());
                        byteBuf.writeByte(action.getBaseByte1());
                        byteBuf.writeByte(action.getBaseByte2());
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        byteBuf.writeBoolean(action.isBool0());
                        break;
                    case 4:
                    case 5:
                        byteBuf.writeByte(action.getBaseByte0());
                        byteBuf.writeByte(action.getBaseByte1());
                        byteBuf.writeByte(action.getBaseByte2());
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        break;
                    case 6:
                        byteBuf.writeByte(action.getByte0());
                        break;
                    case 8:
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        VarInts.writeInt(byteBuf, action.getVarInt1());
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        break;
                    case 17:
                        helper.writeArray(byteBuf, action.getItems(), helper::writeItem);
                        break;
                }
            });
        });
    }

    public StackRequestSlotInfoData readStackRequestSlotInfo(ByteBuf buffer, BedrockPacketHelper helper) {
        return new StackRequestSlotInfoData(
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer)
        );
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
                                byteBuf.readByte(),
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


//                boolean bool0 = false;
//                byte byte0 = 0;
//                int varInt0 = 0;
//                int varInt1 = 0;
//                byte baseByte0 = 0;
//                byte baseByte1 = 0;
//                byte baseByte2 = 0;
//                int baseVarInt0 = 0;
//                byte flagsByte0 = 0;
//                byte flagsByte1 = 0;
//                int flagsVarInt0 = 0;
//                List<ItemData> items = null;
//
//                switch (type) {
//                    case 0:
//                    case 1:
//                    case 2:
//                        baseByte0 = byteBuf.readByte();
//                        baseByte1 = byteBuf.readByte();
//                        baseByte2 = byteBuf.readByte();
//                        baseVarInt0 = VarInts.readInt(byteBuf);
//                        flagsByte0 = byteBuf.readByte();
//                        flagsByte1 = byteBuf.readByte();
//                        flagsVarInt0 = VarInts.readInt(byteBuf);
//                        break;
//                    case 3:
//                        baseByte0 = byteBuf.readByte();
//                        baseByte1 = byteBuf.readByte();
//                        baseByte2 = byteBuf.readByte();
//                        baseVarInt0 = VarInts.readInt(byteBuf);
//                        bool0 = byteBuf.readBoolean();
//                        break;
//                    case 4:
//                    case 5:
//                        baseByte0 = byteBuf.readByte();
//                        baseByte1 = byteBuf.readByte();
//                        baseByte2 = byteBuf.readByte();
//                        baseVarInt0 = VarInts.readInt(byteBuf);
//                        break;
//                    case 6:
//                        byte0 = byteBuf.readByte();
//                        break;
//                    case 8:
//                        varInt0 = VarInts.readInt(byteBuf);
//                        varInt1 = VarInts.readInt(byteBuf);
//                        break;
//                    case 9:
//                    case 10:
//                    case 11:
//                    case 12:
//                    case 13:
//                    case 14:
//                    case 15:
//                        varInt0 = VarInts.readUnsignedInt(byteBuf);
//                        break;
//                    case 17:
//                        items = new ArrayList<>();
//                        helper.readArray(byteBuf, items, helper::readItem);
//                        break;
//                }
//                return new ItemStackActionDeprecated(type, bool0, byte0, varInt0, varInt1, baseByte0, baseByte1, baseByte2,
//                        baseVarInt0, flagsByte0, flagsByte1, flagsVarInt0, items);
            });
            return new ItemStackRequestPacket.Request(requestId, actions);
        });
    }
}
