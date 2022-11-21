package org.cloudburstmc.protocol.bedrock.codec.v554;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v503.BedrockCodecHelper_v503;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

public class BedrockCodecHelper_v554 extends BedrockCodecHelper_v503 {

    private static final TextProcessingEventOrigin[] ORIGINS = TextProcessingEventOrigin.values();
    protected static final ItemDescriptorType[] DESCRIPTOR_TYPES = ItemDescriptorType.values();

    public BedrockCodecHelper_v554(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<StackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ObjectArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = this.stackRequestActionTypes.getType(byteBuf.readByte());
            return readRequestActionData(byteBuf, type);
        });
        List<String> filteredStrings = new ObjectArrayList<>();
        this.readArray(buffer, filteredStrings, this::readString);

        int originVal = buffer.readIntLE();
        TextProcessingEventOrigin origin = originVal == -1 ? null : ORIGINS[originVal]; // new for v552
        return new ItemStackRequest(requestId, actions.toArray(new StackRequestActionData[0]), filteredStrings.toArray(new String[0]), origin);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        super.writeItemStackRequest(buffer, request);
        TextProcessingEventOrigin origin = request.getTextProcessingEventOrigin();
        buffer.writeIntLE(origin == null ? -1 : request.getTextProcessingEventOrigin().ordinal()); // new for v552
    }

    @Override
    public ItemDescriptorWithCount readIngredient(ByteBuf buffer) {
        ItemDescriptorType type = DESCRIPTOR_TYPES[buffer.readUnsignedByte()];

        ItemDescriptor descriptor;
        switch (type) {
            case DEFAULT:
                int itemId = buffer.readShortLE();
                ItemDefinition definition = itemId == 0 ? ItemDefinition.AIR : this.getItemDefinitions().getDefinition(itemId);
                int auxValue = itemId != 0 ? buffer.readShortLE() : 0;
                descriptor = new DefaultDescriptor(definition, auxValue);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(this.readString(buffer), buffer.readUnsignedByte());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(this.readString(buffer));
                break;
            case DEFERRED:
                descriptor = new DeferredDescriptor(this.readString(buffer), buffer.readShortLE());
                break;
            default:
                descriptor = InvalidDescriptor.INSTANCE;
                break;
        }

        return new ItemDescriptorWithCount(descriptor, VarInts.readInt(buffer));
    }

    @Override
    public void writeIngredient(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        ItemDescriptorType type = ingredient.getDescriptor().getType();
        buffer.writeByte(type.ordinal());

        switch (type) {
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) ingredient.getDescriptor();
                boolean empty = defaultDescriptor.getItemId() == null || defaultDescriptor.getItemId().getRuntimeId() == 0;
                buffer.writeShortLE(empty ? 0 : defaultDescriptor.getItemId().getRuntimeId());
                if (!empty) {
                    buffer.writeShortLE(defaultDescriptor.getAuxValue());
                }
                break;
            case MOLANG:
                MolangDescriptor molangDescriptor = (MolangDescriptor) ingredient.getDescriptor();
                this.writeString(buffer, molangDescriptor.getTagExpression());
                buffer.writeByte(molangDescriptor.getMolangVersion());
                break;
            case ITEM_TAG:
                ItemTagDescriptor tagDescriptor = (ItemTagDescriptor) ingredient.getDescriptor();
                this.writeString(buffer, tagDescriptor.getItemTag());
                break;
            case DEFERRED:
                DeferredDescriptor deferredDescriptor = (DeferredDescriptor) ingredient.getDescriptor();
                this.writeString(buffer, deferredDescriptor.getFullName());
                buffer.writeShortLE(deferredDescriptor.getAuxValue());
                break;
        }
        VarInts.writeInt(buffer, ingredient.getCount());
    }
}
