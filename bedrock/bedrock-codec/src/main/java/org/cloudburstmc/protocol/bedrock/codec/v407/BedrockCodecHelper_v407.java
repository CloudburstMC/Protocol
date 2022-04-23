package org.cloudburstmc.protocol.bedrock.codec.v407;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.v390.BedrockCodecHelper_v390;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventorySource;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.AutoCraftRecipeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.BeaconPaymentStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.ConsumeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftCreativeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftNonImplementedStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftRecipeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftResultsDeprecatedStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CreateStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.DestroyStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.DropStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.LabTableCombineRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.PlaceStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.RecipeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.SwapStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.TakeStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.TransferStackRequestActionData;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public class BedrockCodecHelper_v407 extends BedrockCodecHelper_v390 {

    protected final TypeMap<StackRequestActionType> stackRequestActionTypes;

    public BedrockCodecHelper_v407(TypeMap<EntityData> entityData, TypeMap<EntityData.Type> entityDataTypes,
                                   TypeMap<EntityFlag> entityFlags, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<StackRequestActionType> stackRequestActionTypes) {
        super(entityData, entityDataTypes, entityFlags, gameRulesTypes);
        this.stackRequestActionTypes = stackRequestActionTypes;
    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {

        return new EntityLinkData(
                VarInts.readLong(buffer),
                VarInts.readLong(buffer),
                EntityLinkData.Type.byId(buffer.readUnsignedByte()),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData entityLink) {
        checkNotNull(entityLink, "entityLink");

        VarInts.writeLong(buffer, entityLink.getFrom());
        VarInts.writeLong(buffer, entityLink.getTo());
        buffer.writeByte(entityLink.getType().ordinal());
        buffer.writeBoolean(entityLink.isImmediate());
        buffer.writeBoolean(entityLink.isRiderInitiated());
    }

    @Override
    public boolean readInventoryActions(ByteBuf buffer, List<InventoryActionData> actions) {
        boolean hasNetworkIds = buffer.readBoolean();
        this.readArray(buffer, actions, (buf, helper) -> {
            InventorySource source = this.readSource(buf);
            int slot = VarInts.readUnsignedInt(buf);
            ItemData fromItem = helper.readItem(buf);
            ItemData toItem = helper.readItem(buf);
            int networkStackId = 0;
            if (hasNetworkIds) {
                networkStackId = VarInts.readInt(buf);
            }

            return new InventoryActionData(source, slot, fromItem, toItem, networkStackId);
        });
        return hasNetworkIds;
    }

    @Override
    public void writeInventoryActions(ByteBuf buffer, List<InventoryActionData> actions, boolean hasNetworkIds) {
        buffer.writeBoolean(hasNetworkIds);
        this.writeArray(buffer, actions, (buf, helper, action) -> {
            this.writeSource(buffer, action.getSource());
            VarInts.writeUnsignedInt(buffer, action.getSlot());
            helper.writeItem(buffer, action.getFromItem());
            helper.writeItem(buffer, action.getToItem());
            if (hasNetworkIds) {
                VarInts.writeInt(buffer, action.getStackNetworkId());
            }
        });
    }

    @Override
    public ItemData readNetItem(ByteBuf buffer) {
        int netId = VarInts.readInt(buffer);
        ItemData item = this.readItem(buffer);
        item.setNetId(netId);
        return item;
    }

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item) {
        VarInts.writeInt(buffer, item.getNetId());
        this.writeItem(buffer, item);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = this.stackRequestActionTypes.getType(byteBuf.readByte());
            return readRequestActionData(byteBuf, type);
        });
        return new ItemStackRequest(requestId, actions.toArray(new StackRequestActionData[0]), new String[0]);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            StackRequestActionType type = action.getType();
            byteBuf.writeByte(this.stackRequestActionTypes.getId(type));
            writeRequestActionData(byteBuf, action);
        });
    }

    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action) {
        switch (action.getType()) {
            case TAKE:
            case PLACE:
                byteBuf.writeByte(((TransferStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((TransferStackRequestActionData) action).getSource());
                writeStackRequestSlotInfo(byteBuf, ((TransferStackRequestActionData) action).getDestination());
                break;
            case SWAP:
                writeStackRequestSlotInfo(byteBuf, ((SwapStackRequestActionData) action).getSource());
                writeStackRequestSlotInfo(byteBuf, ((SwapStackRequestActionData) action).getDestination());
                break;
            case DROP:
                byteBuf.writeByte(((DropStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((DropStackRequestActionData) action).getSource());
                byteBuf.writeBoolean(((DropStackRequestActionData) action).isRandomly());
                break;
            case DESTROY:
                byteBuf.writeByte(((DestroyStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((DestroyStackRequestActionData) action).getSource());
                break;
            case CONSUME:
                byteBuf.writeByte(((ConsumeStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((ConsumeStackRequestActionData) action).getSource());
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
                this.writeArray(byteBuf, ((CraftResultsDeprecatedStackRequestActionData) action).getResultItems(), (buf2, item) -> this.writeItem(buf2, item));
                byteBuf.writeByte(((CraftResultsDeprecatedStackRequestActionData) action).getTimesCrafted());
                break;
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + action.getType());
        }
    }

    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type) {
        switch (type) {
            case TAKE:
                return new TakeStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case PLACE:
                return new PlaceStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case SWAP:
                return new SwapStackRequestActionData(
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case DROP:
                return new DropStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf),
                        byteBuf.readBoolean()
                );
            case DESTROY:
                return new DestroyStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CONSUME:
                return new ConsumeStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf)
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
                        VarInts.readUnsignedInt(byteBuf), (byte) 0
                );
            case CRAFT_CREATIVE:
                return new CraftCreativeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf)
                );
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                return new CraftNonImplementedStackRequestActionData();
            case CRAFT_RESULTS_DEPRECATED:
                return new CraftResultsDeprecatedStackRequestActionData(
                        this.readArray(byteBuf, new ItemData[0], this::readItem),
                        byteBuf.readByte()
                );
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + type);
        }
    }

    protected StackRequestSlotInfoData readStackRequestSlotInfo(ByteBuf buffer) {
        return new StackRequestSlotInfoData(
                ContainerSlotType.values()[buffer.readByte()],
                buffer.readByte(),
                VarInts.readInt(buffer)
        );
    }

    protected void writeStackRequestSlotInfo(ByteBuf buffer, StackRequestSlotInfoData data) {
        buffer.writeByte(data.getContainer().ordinal());
        buffer.writeByte(data.getSlot());
        VarInts.writeInt(buffer, data.getStackNetworkId());
    }
}
