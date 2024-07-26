package org.cloudburstmc.protocol.bedrock.codec.v407;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v390.BedrockCodecHelper_v390;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.DefaultDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.InvalidDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventorySource;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public class BedrockCodecHelper_v407 extends BedrockCodecHelper_v390 {

    protected final TypeMap<ItemStackRequestActionType> stackRequestActionTypes;
    protected final TypeMap<ContainerSlotType> containerSlotTypes;

    public BedrockCodecHelper_v407(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes);
        this.stackRequestActionTypes = stackRequestActionTypes;
        this.containerSlotTypes = containerSlotTypes;
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
        }, 64); // 64 should be enough
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
        List<ItemStackRequestAction> actions = new ObjectArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            ItemStackRequestActionType type = this.stackRequestActionTypes.getType(byteBuf.readByte());
            return readRequestActionData(byteBuf, type);
        }, 32);
        return new ItemStackRequest(requestId, actions.toArray(new ItemStackRequestAction[0]), new String[0]);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            ItemStackRequestActionType type = action.getType();
            byteBuf.writeByte(this.stackRequestActionTypes.getId(type));
            writeRequestActionData(byteBuf, action);
        });
    }

    protected void writeRequestActionData(ByteBuf byteBuf, ItemStackRequestAction action) {
        switch (action.getType()) {
            case TAKE:
            case PLACE:
                byteBuf.writeByte(((TransferItemStackRequestAction) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((TransferItemStackRequestAction) action).getSource());
                writeStackRequestSlotInfo(byteBuf, ((TransferItemStackRequestAction) action).getDestination());
                break;
            case SWAP:
                writeStackRequestSlotInfo(byteBuf, ((SwapAction) action).getSource());
                writeStackRequestSlotInfo(byteBuf, ((SwapAction) action).getDestination());
                break;
            case DROP:
                byteBuf.writeByte(((DropAction) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((DropAction) action).getSource());
                byteBuf.writeBoolean(((DropAction) action).isRandomly());
                break;
            case DESTROY:
                byteBuf.writeByte(((DestroyAction) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((DestroyAction) action).getSource());
                break;
            case CONSUME:
                byteBuf.writeByte(((ConsumeAction) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((ConsumeAction) action).getSource());
                break;
            case CREATE:
                byteBuf.writeByte(((CreateAction) action).getSlot());
                break;
            case LAB_TABLE_COMBINE:
                break;
            case BEACON_PAYMENT:
                VarInts.writeInt(byteBuf, ((BeaconPaymentAction) action).getPrimaryEffect());
                VarInts.writeInt(byteBuf, ((BeaconPaymentAction) action).getSecondaryEffect());
                break;
            case CRAFT_RECIPE:
            case CRAFT_RECIPE_AUTO:
                VarInts.writeUnsignedInt(byteBuf, ((RecipeItemStackRequestAction) action).getRecipeNetworkId());
                break;
            case CRAFT_CREATIVE:
                VarInts.writeUnsignedInt(byteBuf, ((CraftCreativeAction) action).getCreativeItemNetworkId());
                break;
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                break;
            case CRAFT_RESULTS_DEPRECATED:
                this.writeArray(byteBuf, ((CraftResultsDeprecatedAction) action).getResultItems(), (buf2, item) -> this.writeItem(buf2, item));
                byteBuf.writeByte(((CraftResultsDeprecatedAction) action).getTimesCrafted());
                break;
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + action.getType());
        }
    }

    protected ItemStackRequestAction readRequestActionData(ByteBuf byteBuf, ItemStackRequestActionType type) {
        switch (type) {
            case TAKE:
                return new TakeAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case PLACE:
                return new PlaceAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case SWAP:
                return new SwapAction(
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case DROP:
                return new DropAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf),
                        byteBuf.readBoolean()
                );
            case DESTROY:
                return new DestroyAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CONSUME:
                return new ConsumeAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CREATE:
                return new CreateAction(
                        byteBuf.readUnsignedByte()
                );
            case LAB_TABLE_COMBINE:
                return new LabTableCombineAction();
            case BEACON_PAYMENT:
                return new BeaconPaymentAction(
                        VarInts.readInt(byteBuf),
                        VarInts.readInt(byteBuf)
                );
            case CRAFT_RECIPE:
                return new CraftRecipeAction(
                        VarInts.readUnsignedInt(byteBuf), 0
                );
            case CRAFT_RECIPE_AUTO:
                return new AutoCraftRecipeAction(
                        VarInts.readUnsignedInt(byteBuf), 0, Collections.emptyList(), 0
                );
            case CRAFT_CREATIVE:
                return new CraftCreativeAction(
                        VarInts.readUnsignedInt(byteBuf), 0
                );
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                return new CraftNonImplementedAction();
            case CRAFT_RESULTS_DEPRECATED:
                return new CraftResultsDeprecatedAction(
                        this.readArray(byteBuf, new ItemData[0], this::readItem),
                        byteBuf.readUnsignedByte()
                );
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + type);
        }
    }

    protected ItemStackRequestSlotData readStackRequestSlotInfo(ByteBuf buffer) {
        return new ItemStackRequestSlotData(
                this.readContainerSlotType(buffer),
                buffer.readUnsignedByte(),
                VarInts.readInt(buffer),
                null
        );
    }

    protected void writeStackRequestSlotInfo(ByteBuf buffer, ItemStackRequestSlotData data) {
        this.writeContainerSlotType(buffer, data.getContainer());
        buffer.writeByte(data.getSlot());
        VarInts.writeInt(buffer, data.getStackNetworkId());
    }

    @Override
    public ContainerSlotType readContainerSlotType(ByteBuf buffer) {
        return this.containerSlotTypes.getType(buffer.readByte());
    }

    @Override
    public void writeContainerSlotType(ByteBuf buffer, ContainerSlotType slotType) {
        buffer.writeByte(this.containerSlotTypes.getId(slotType));
    }

    @Override
    public ItemDescriptorWithCount readIngredient(ByteBuf buffer) {
        int runtimeId = VarInts.readInt(buffer);
        if (runtimeId == 0) {
            // We don't need to read anything extra.
            return ItemDescriptorWithCount.EMPTY;
        }
        ItemDefinition definition = this.getItemDefinitions().getDefinition(runtimeId);

        int meta = fromAuxValue(VarInts.readInt(buffer));
        int count = VarInts.readInt(buffer);

        return new ItemDescriptorWithCount(new DefaultDescriptor(definition, meta), count);
    }

    @Override
    public void writeIngredient(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        requireNonNull(ingredient, "ingredient is null");
        if (ingredient == ItemDescriptorWithCount.EMPTY || ingredient.getDescriptor() == InvalidDescriptor.INSTANCE) {
            VarInts.writeInt(buffer, 0);
            return;
        }

        checkArgument(ingredient.getDescriptor() instanceof DefaultDescriptor, "Descriptor must be of type DefaultDescriptor");
        DefaultDescriptor descriptor = (DefaultDescriptor) ingredient.getDescriptor();

        VarInts.writeInt(buffer, descriptor.getItemId().getRuntimeId());
        VarInts.writeInt(buffer, toAuxValue(descriptor.getAuxValue()));
        VarInts.writeInt(buffer, ingredient.getCount());
    }

    @Override
    public void writeItemStackResponseContainer(ByteBuf buffer, ItemStackResponseContainer container) {
        this.writeContainerSlotType(buffer, container.getContainer());
        this.writeArray(buffer, container.getItems(), this::writeItemEntry);
    }

    @Override
    public ItemStackResponseContainer readItemStackResponseContainer(ByteBuf buffer) {
        ContainerSlotType slotType = this.readContainerSlotType(buffer);
        List<ItemStackResponseSlot> itemEntries = new ArrayList<>();
        this.readArray(buffer, itemEntries, this::readItemEntry);
        return new ItemStackResponseContainer(slotType, itemEntries, null);
    }

    protected ItemStackResponseSlot readItemEntry(ByteBuf buffer) {
        return new ItemStackResponseSlot(
                buffer.readUnsignedByte(),
                buffer.readUnsignedByte(),
                buffer.readUnsignedByte(),
                VarInts.readInt(buffer),
                "",
                0);
    }

    protected void writeItemEntry(ByteBuf buffer, ItemStackResponseSlot itemEntry) {
        buffer.writeByte(itemEntry.getSlot());
        buffer.writeByte(itemEntry.getHotbarSlot());
        buffer.writeByte(itemEntry.getCount());
        VarInts.writeInt(buffer, itemEntry.getStackNetworkId());
    }

    protected int fromAuxValue(int value) {
        return value == 0x7fff ? -1 : value;
    }

    protected int toAuxValue(int value) {
        return value == -1 ? 0x7fff : value;
    }
}
