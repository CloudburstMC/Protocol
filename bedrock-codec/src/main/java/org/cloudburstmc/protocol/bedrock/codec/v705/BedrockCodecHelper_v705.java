package org.cloudburstmc.protocol.bedrock.codec.v705;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.*;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

public class BedrockCodecHelper_v705 extends BedrockCodecHelper_v575 {

    public BedrockCodecHelper_v705(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
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
            byteBuf.writeByte(((AutoCraftRecipeAction) action).getNumberOfRequestedCrafts());
            byteBuf.writeByte(((AutoCraftRecipeAction) action).getTimesCrafted());
            List<ItemDescriptorWithCount> ingredients = ((AutoCraftRecipeAction) action).getIngredients();
            byteBuf.writeByte(ingredients.size());
            writeArray(byteBuf, ingredients, this::writeIngredient);
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
            int timesCrafted = byteBuf.readUnsignedByte();
            int numberOfRequestedCrafts = byteBuf.readUnsignedByte();
            List<ItemDescriptorWithCount> ingredients = new ObjectArrayList<>();
            this.readArray(byteBuf, ingredients, ByteBuf::readUnsignedByte, this::readIngredient);
            return new AutoCraftRecipeAction(recipeNetworkId, timesCrafted, ingredients, numberOfRequestedCrafts);
        } else {
            return super.readRequestActionData(byteBuf, type);
        }
    }
}