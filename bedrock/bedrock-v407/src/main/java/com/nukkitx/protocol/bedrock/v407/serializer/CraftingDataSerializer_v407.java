package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.DefaultDescriptor;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.nukkitx.network.util.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v407 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v407 INSTANCE = new CraftingDataSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
        helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    this.writeShapelessRecipe(buf, helper, craftingData, session);
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    this.writeShapedRecipe(buf, helper, craftingData, session);
                    break;
                case FURNACE:
                    this.writeFurnaceRecipe(buf, helper, craftingData, session);
                    break;
                case FURNACE_DATA:
                    this.writeFurnaceDataRecipe(buf, helper, craftingData, session);
                    break;
                case MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });

        // Potions
        helper.writeArray(buffer, packet.getPotionMixData(), this::writePotionRecipe);

        // Potion Container Change
        helper.writeArray(buffer, packet.getContainerMixData(), this::writeContainerChangeRecipe);

        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
        helper.readArray(buffer, packet.getCraftingData(), buf -> {
            int typeInt = VarInts.readInt(buf);
            CraftingDataType type = CraftingDataType.byId(typeInt);

            switch (type) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    return this.readShapelessRecipe(buf, helper, type, session);
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    return this.readShapedRecipe(buf, helper, type, session);
                case FURNACE:
                    return this.readFurnaceRecipe(buf, helper, type, session);
                case FURNACE_DATA:
                    return this.readFurnaceDataRecipe(buf, helper, type, session);
                case MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });

        // Potions
        helper.readArray(buffer, packet.getPotionMixData(), this::readPotionRecipe);

        // Potion Container Change
        helper.readArray(buffer, packet.getContainerMixData(), this::readContainerChangeRecipe);

        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        String recipeId = helper.readString(buffer);
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, this::readIngredient);

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf, session));

        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, -1, -1, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        helper.writeString(buffer, data.getRecipeId());
        helper.writeArray(buffer, data.getInputDescriptors(), this::writeIngredient);
        helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItemInstance(buf, item, session));

        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(readIngredient(buffer, helper));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf, session));
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        helper.writeString(buffer, data.getRecipeId());
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemDescriptorWithCount> inputs = data.getInputDescriptors();
        for (int i = 0; i < count; i++) {
            writeIngredient(buffer, helper, inputs.get(i));
        }
        helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItemInstance(buf, item, session));
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        int inputId = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer, session)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, -1, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        VarInts.writeInt(buffer, data.getInputId());
        helper.writeItemInstance(buffer, data.getOutputs().get(0), session);
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readFurnaceDataRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer, session)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceDataRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputDamage());
        helper.writeItemInstance(buffer, data.getOutputs().get(0), session);
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return CraftingData.fromMulti(uuid, networkId);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
        helper.writeUuid(buffer, data.getUuid());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected ItemDescriptorWithCount readIngredient(ByteBuf buffer, BedrockPacketHelper helper) {
        requireNonNull(buffer, "buffer is null");

        int id = VarInts.readInt(buffer);

        if (id == 0) {
            // We don't need to read anything extra.
            return ItemDescriptorWithCount.EMPTY;
        }

        int meta = VarInts.readInt(buffer);
        int count = VarInts.readInt(buffer);

        return new ItemDescriptorWithCount(new DefaultDescriptor(id, meta), count);
    }

    protected void writeIngredient(ByteBuf buffer, BedrockPacketHelper helper, ItemDescriptorWithCount ingredient) {
        requireNonNull(buffer, "buffer is null");
        requireNonNull(ingredient, "ingredient is null");
        checkArgument(ingredient.getDescriptor() instanceof DefaultDescriptor, "ingredient descriptor must be a DefaultDescriptor");
        DefaultDescriptor descriptor = (DefaultDescriptor) ingredient.getDescriptor();

        VarInts.writeInt(buffer, descriptor.getItemId());

        if (descriptor.getItemId() == 0) {
            return;
        }

        VarInts.writeInt(buffer, descriptor.getAuxValue());
        VarInts.writeInt(buffer, ingredient.getCount());
    }

    public PotionMixData readPotionRecipe(ByteBuf buffer) {
        requireNonNull(buffer, "buffer is null");

        return new PotionMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    public void writePotionRecipe(ByteBuf buffer, PotionMixData data) {
        requireNonNull(buffer, "buffer is null");
        requireNonNull(data, "data is null");

        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputMeta());
        VarInts.writeInt(buffer, data.getReagentId());
        VarInts.writeInt(buffer, data.getReagentMeta());
        VarInts.writeInt(buffer, data.getOutputId());
        VarInts.writeInt(buffer, data.getOutputMeta());
    }

    public ContainerMixData readContainerChangeRecipe(ByteBuf buffer) {
        requireNonNull(buffer, "buffer is null");

        return new ContainerMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    public void writeContainerChangeRecipe(ByteBuf buffer, ContainerMixData data) {
        requireNonNull(buffer, "buffer is null");
        requireNonNull(data, "data is null");

        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getReagentId());
        VarInts.writeInt(buffer, data.getOutputId());
    }
}
