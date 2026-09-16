package org.cloudburstmc.protocol.bedrock.codec.v2193;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v2168.BedrockCodecHelper_v2168;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventorySource;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v2193 extends BedrockCodecHelper_v2168 {

    public BedrockCodecHelper_v2193(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                    TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    protected ItemStackResponseSlot readItemEntry(ByteBuf buffer) {
        int slot = buffer.readUnsignedByte();
        int hotbarSlot = buffer.readUnsignedByte();
        int count = buffer.readUnsignedByte();
        int stackNetworkId = buffer.readBoolean() ? VarInts.readInt(buffer) : 0;
        String customName = this.readString(buffer);
        String filteredCustomName = this.readOptional(buffer, null, this::readString);
        int durabilityCorrection = VarInts.readInt(buffer);
        return new ItemStackResponseSlot(slot, hotbarSlot, count, stackNetworkId,
                customName, durabilityCorrection, filteredCustomName);

    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, ItemStackResponseSlot itemEntry) {
        buffer.writeByte(itemEntry.getSlot());
        buffer.writeByte(itemEntry.getHotbarSlot());
        buffer.writeByte(itemEntry.getCount());
        this.writeOptional(buffer, id->id > 0, itemEntry.getStackNetworkId(), VarInts::writeInt);
        this.writeString(buffer, itemEntry.getCustomName());
        this.writeOptionalNull(buffer, itemEntry.getFilteredCustomName(), this::writeString);
        VarInts.writeInt(buffer, itemEntry.getDurabilityCorrection());
    }

    @Override
    public InventorySource readSource(ByteBuf buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer));

        int containerId = 0;
        InventorySource.Flag flag = null;
        if (buffer.readBoolean()) containerId = buffer.readByte();
        if (buffer.readBoolean()) flag = InventorySource.Flag.values()[VarInts.readUnsignedInt(buffer)];
        switch (type) {
            case CONTAINER:
                return InventorySource.fromContainerWindowId(containerId);
            case GLOBAL:
                return InventorySource.fromGlobalInventory();
            case WORLD_INTERACTION:
                if (flag == null) throw new IllegalStateException();
                return InventorySource.fromWorldInteraction(flag);
            case CREATIVE:
                return InventorySource.fromCreativeInventory();
            case NON_IMPLEMENTED_TODO:
                return InventorySource.fromNonImplementedTodo(containerId);
            case UNTRACKED_INTERACTION_UI:
                return InventorySource.fromUntrackedInteractionUI(containerId);
            default:
                return InventorySource.fromInvalid();
        }
    }

    @Override
    public void writeSource(ByteBuf buffer, InventorySource inventorySource) {
        requireNonNull(inventorySource, "InventorySource was null");

        VarInts.writeUnsignedInt(buffer, inventorySource.getType().id());

        switch (inventorySource.getType()) {
            case CONTAINER:
            case NON_IMPLEMENTED_TODO:
                buffer.writeBoolean(true);
                buffer.writeByte(inventorySource.getContainerId());
                break;
            default:
                buffer.writeBoolean(false);
                break;
        }

        switch (inventorySource.getType()) {
            case WORLD_INTERACTION:
                buffer.writeBoolean(true);
                VarInts.writeUnsignedInt(buffer, inventorySource.getFlag().ordinal());
                break;
            default:
                buffer.writeBoolean(false);
                break;
        }
    }
}
