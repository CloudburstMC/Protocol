package org.cloudburstmc.protocol.bedrock.codec.v712;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class BedrockCodecHelper_v712 extends BedrockCodecHelper_v575 {

    public BedrockCodecHelper_v712(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData entityLink) {
        super.writeEntityLink(buffer, entityLink);
        buffer.writeFloatLE(entityLink.getVehicleAngularVelocity());
    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        return new EntityLinkData(
                VarInts.readLong(buffer),
                VarInts.readLong(buffer),
                EntityLinkData.Type.byId(buffer.readUnsignedByte()),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readFloatLE()
        );
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, ItemStackRequestAction action) {
        if (action.getType().equals(ItemStackRequestActionType.CRAFT_RECIPE)) {
            VarInts.writeUnsignedInt(byteBuf, ((RecipeItemStackRequestAction) action).getRecipeNetworkId());
            byteBuf.writeByte(((RecipeItemStackRequestAction) action).getNumberOfRequestedCrafts());
        } else if (action.getType().equals(ItemStackRequestActionType.CRAFT_CREATIVE)) {
            VarInts.writeUnsignedInt(byteBuf, ((CraftCreativeAction) action).getCreativeItemNetworkId());
            byteBuf.writeByte(((CraftCreativeAction) action).getNumberOfRequestedCrafts());
        } else if (action.getType().equals(ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT)) {
            VarInts.writeUnsignedInt(byteBuf, ((CraftGrindstoneAction) action).getRecipeNetworkId());
            byteBuf.writeByte(((CraftGrindstoneAction) action).getNumberOfRequestedCrafts());
            VarInts.writeInt(byteBuf, ((CraftGrindstoneAction) action).getRepairCost());
        } else if (action.getType().equals(ItemStackRequestActionType.CRAFT_RECIPE_AUTO)) {
            VarInts.writeUnsignedInt(byteBuf, ((AutoCraftRecipeAction) action).getRecipeNetworkId());
            byteBuf.writeByte(((AutoCraftRecipeAction) action).getNumberOfRequestedCrafts()); // since v712
            byteBuf.writeByte(((AutoCraftRecipeAction) action).getTimesCrafted());
            List<ItemDescriptorWithCount> ingredients = ((AutoCraftRecipeAction) action).getIngredients();
            byteBuf.writeByte(ingredients.size());
            writeArray(byteBuf, ingredients, this::writeIngredient);
        } else if (action.getType().equals(ItemStackRequestActionType.CRAFT_LOOM)) {
            this.writeString(byteBuf, ((CraftLoomAction) action).getPatternId());
            byteBuf.writeByte(((CraftLoomAction) action).getTimesCrafted());
        } else {
            super.writeRequestActionData(byteBuf, action);
        }
    }

    @Override
    protected ItemStackRequestAction readRequestActionData(ByteBuf byteBuf, ItemStackRequestActionType type) {
        if (type.equals(ItemStackRequestActionType.CRAFT_RECIPE)) {
            return new CraftRecipeAction(VarInts.readUnsignedInt(byteBuf), byteBuf.readByte());
        } else if (type.equals(ItemStackRequestActionType.CRAFT_CREATIVE)) {
            return new CraftCreativeAction(VarInts.readUnsignedInt(byteBuf), byteBuf.readByte());
        } else if (type.equals(ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT)) {
            return new CraftGrindstoneAction(VarInts.readUnsignedInt(byteBuf), byteBuf.readByte(), VarInts.readInt(byteBuf));
        } else if (type.equals(ItemStackRequestActionType.CRAFT_RECIPE_AUTO)) {
            int recipeNetworkId = VarInts.readUnsignedInt(byteBuf);
            int numberOfRequestedCrafts = byteBuf.readUnsignedByte(); // since v712
            int timesCrafted = byteBuf.readUnsignedByte();
            List<ItemDescriptorWithCount> ingredients = new ObjectArrayList<>();
            this.readArray(byteBuf, ingredients, ByteBuf::readUnsignedByte, this::readIngredient);
            return new AutoCraftRecipeAction(recipeNetworkId, timesCrafted, ingredients, numberOfRequestedCrafts);
        } else if (type.equals(ItemStackRequestActionType.CRAFT_LOOM)) {
            String patternId = this.readString(byteBuf);
            int timesCrafted = byteBuf.readUnsignedByte();
            return new CraftLoomAction(patternId, timesCrafted);
        } else {
            return super.readRequestActionData(byteBuf, type);
        }
    }

    @Override
    protected ItemStackRequestSlotData readStackRequestSlotInfo(ByteBuf buffer) {
        FullContainerName containerName = this.readFullContainerName(buffer);
        return new ItemStackRequestSlotData(
                containerName.getContainer(),
                buffer.readUnsignedByte(),
                VarInts.readInt(buffer),
                containerName
        );
    }

    @Override
    protected void writeStackRequestSlotInfo(ByteBuf buffer, ItemStackRequestSlotData data) {
        this.writeFullContainerName(buffer, data.getContainerName());
        buffer.writeByte(data.getSlot());
        VarInts.writeInt(buffer, data.getStackNetworkId());
    }

    @Override
    public void writeItemStackResponseContainer(ByteBuf buffer, ItemStackResponseContainer container) {
        this.writeFullContainerName(buffer, container.getContainerName());
        this.writeArray(buffer, container.getItems(), this::writeItemEntry);
    }

    @Override
    public ItemStackResponseContainer readItemStackResponseContainer(ByteBuf buffer) {
        FullContainerName containerName = this.readFullContainerName(buffer);
        List<ItemStackResponseSlot> itemEntries = new ArrayList<>();
        this.readArray(buffer, itemEntries, this::readItemEntry);
        return new ItemStackResponseContainer(containerName.getContainer(), itemEntries, containerName);
    }

    @Override
    public void writeItemUse(ByteBuf buffer, InventoryTransactionPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getActionType());
        VarInts.writeUnsignedInt(buffer, packet.getTriggerType().ordinal());
        this.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getBlockFace());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        this.writeItem(buffer, packet.getItemInHand());
        this.writeVector3f(buffer, packet.getPlayerPosition());
        this.writeVector3f(buffer, packet.getClickPosition());
        VarInts.writeUnsignedInt(buffer, packet.getBlockDefinition().getRuntimeId());
        VarInts.writeUnsignedInt(buffer, packet.getClientInteractPrediction().ordinal());
    }

    @Override
    public void readItemUse(ByteBuf buffer, InventoryTransactionPacket packet) {
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setTriggerType(ItemUseTransaction.TriggerType.values()[VarInts.readUnsignedInt(buffer)]);
        packet.setBlockPosition(this.readBlockPosition(buffer));
        packet.setBlockFace(VarInts.readInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(this.readItem(buffer));
        packet.setPlayerPosition(this.readVector3f(buffer));
        packet.setClickPosition(this.readVector3f(buffer));
        packet.setBlockDefinition(this.blockDefinitions.getDefinition(VarInts.readUnsignedInt(buffer)));
        packet.setClientInteractPrediction(ItemUseTransaction.PredictedResult.values()[VarInts.readUnsignedInt(buffer)]);
    }

    @Override
    public void writeFullContainerName(ByteBuf buffer, FullContainerName containerName) {
        this.writeContainerSlotType(buffer, containerName.getContainer());
        buffer.writeIntLE(containerName.getDynamicId() == null ? 0 : containerName.getDynamicId());
    }

    @Override
    public FullContainerName readFullContainerName(ByteBuf buffer) {
        return new FullContainerName(this.readContainerSlotType(buffer), buffer.readIntLE());
    }
}