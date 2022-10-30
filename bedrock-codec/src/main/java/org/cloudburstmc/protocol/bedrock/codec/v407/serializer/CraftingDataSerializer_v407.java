package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.DefaultDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.InvalidDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v407 implements BedrockPacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v407 INSTANCE = new CraftingDataSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
            VarInts.writeInt(buf, craftingData.getType().ordinal());
            switch (craftingData.getType()) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    this.writeShapelessRecipe(buf, helper, craftingData);
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    this.writeShapedRecipe(buf, helper, craftingData);
                    break;
                case FURNACE:
                    this.writeFurnaceRecipe(buf, helper, craftingData);
                    break;
                case FURNACE_DATA:
                    this.writeFurnaceDataRecipe(buf, helper, craftingData);
                    break;
                case MULTI:
                    this.writeMultiRecipe(buf, helper, craftingData);
                    break;
            }
        });

        // Potions
        helper.writeArray(buffer, packet.getPotionMixData(), this::writePotionRecipe);

        // Potion Container Change
        helper.writeArray(buffer, packet.getContainerMixData(), this::writeContainerMixRecipe);

        buffer.writeBoolean(packet.isCleanRecipes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataPacket packet) {
        helper.readArray(buffer, packet.getCraftingData(), buf -> {
            int typeInt = VarInts.readInt(buf);
            CraftingDataType type = CraftingDataType.byId(typeInt);

            switch (type) {
                case SHAPELESS:
                case SHAPELESS_CHEMISTRY:
                case SHULKER_BOX:
                    return this.readShapelessRecipe(buf, helper, type);
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    return this.readShapedRecipe(buf, helper, type);
                case FURNACE:
                    return this.readFurnaceRecipe(buf, helper, type);
                case FURNACE_DATA:
                    return this.readFurnaceDataRecipe(buf, helper, type);
                case MULTI:
                    return this.readMultiRecipe(buf, helper, type);
                default:
                    throw new IllegalArgumentException("Unhandled crafting data type: " + type);
            }
        });

        // Potions
        helper.readArray(buffer, packet.getPotionMixData(), this::readPotionRecipe);

        // Potion Container Change
        helper.readArray(buffer, packet.getContainerMixData(), this::readContainerMixRecipe);

        packet.setCleanRecipes(buffer.readBoolean());
    }

    protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>();
        helper.readArray(buffer, inputs, this::readIngredient);

        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);

        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, -1, -1, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapelessRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        helper.writeString(buffer, data.getRecipeId());
        helper.writeArray(buffer, data.getInputs(), this::writeIngredient);
        helper.writeArray(buffer, data.getOutputs(), helper::writeItemInstance);

        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        String recipeId = helper.readString(buffer);
        int width = VarInts.readInt(buffer);
        int height = VarInts.readInt(buffer);
        int inputCount = width * height;
        List<ItemDescriptorWithCount> inputs = new ObjectArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputs.add(this.readIngredient(buffer, helper));
        }
        List<ItemData> outputs = new ObjectArrayList<>();
        helper.readArray(buffer, outputs, helper::readItemInstance);
        UUID uuid = helper.readUuid(buffer);
        String craftingTag = helper.readString(buffer);
        int priority = VarInts.readInt(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
    }

    protected void writeShapedRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        helper.writeString(buffer, data.getRecipeId());
        VarInts.writeInt(buffer, data.getWidth());
        VarInts.writeInt(buffer, data.getHeight());
        int count = data.getWidth() * data.getHeight();
        List<ItemDescriptorWithCount> inputs = data.getInputs();
        for (int i = 0; i < count; i++) {
            this.writeIngredient(buffer, helper, inputs.get(i));
        }
        helper.writeArray(buffer, data.getOutputs(), helper::writeItemInstance);
        helper.writeUuid(buffer, data.getUuid());
        helper.writeString(buffer, data.getCraftingTag());
        VarInts.writeInt(buffer, data.getPriority());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, -1, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        VarInts.writeInt(buffer, data.getInputId());
        helper.writeItemInstance(buffer, data.getOutputs().get(0));
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readFurnaceDataRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        int inputId = VarInts.readInt(buffer);
        int inputDamage = VarInts.readInt(buffer);
        List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer)));
        String craftingTag = helper.readString(buffer);
        return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                null, craftingTag);
    }

    protected void writeFurnaceDataRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputDamage());
        helper.writeItemInstance(buffer, data.getOutputs().get(0));
        helper.writeString(buffer, data.getCraftingTag());
    }

    protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingDataType type) {
        UUID uuid = helper.readUuid(buffer);
        int networkId = VarInts.readUnsignedInt(buffer);
        return CraftingData.fromMulti(uuid, networkId);
    }

    protected void writeMultiRecipe(ByteBuf buffer, BedrockCodecHelper helper, CraftingData data) {
        helper.writeUuid(buffer, data.getUuid());
        VarInts.writeUnsignedInt(buffer, data.getNetworkId());
    }

    protected ItemDescriptorWithCount readIngredient(ByteBuf buffer, BedrockCodecHelper helper) {
        int runtimeId = VarInts.readInt(buffer);
        if (runtimeId == 0) {
            // We don't need to read anything extra.
            return ItemDescriptorWithCount.EMPTY;
        }
        ItemDefinition definition = helper.getItemDefinitions().getDefinition(runtimeId);

        int meta = fromAuxValue(VarInts.readInt(buffer));
        int count = VarInts.readInt(buffer);

        return new ItemDescriptorWithCount(new DefaultDescriptor(definition, meta), count);
    }

    protected void writeIngredient(ByteBuf buffer, BedrockCodecHelper helper, ItemDescriptorWithCount ingredient) {
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

    protected PotionMixData readPotionRecipe(ByteBuf buffer) {
        return new PotionMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    protected void writePotionRecipe(ByteBuf buffer, PotionMixData data) {
        checkNotNull(data, "data is null");

        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getInputMeta());
        VarInts.writeInt(buffer, data.getReagentId());
        VarInts.writeInt(buffer, data.getReagentMeta());
        VarInts.writeInt(buffer, data.getOutputId());
        VarInts.writeInt(buffer, data.getOutputMeta());
    }

    protected ContainerMixData readContainerMixRecipe(ByteBuf buffer) {
        return new ContainerMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    protected void writeContainerMixRecipe(ByteBuf buffer, ContainerMixData data) {
        checkNotNull(data, "data is null");

        VarInts.writeInt(buffer, data.getInputId());
        VarInts.writeInt(buffer, data.getReagentId());
        VarInts.writeInt(buffer, data.getOutputId());
    }

    protected int fromAuxValue(int value) {
        return value == 0x7fff ? -1 : value;
    }

    protected int toAuxValue(int value) {
        return value == -1 ? 0x7fff : value;
    }
}
