package com.nukkitx.protocol.bedrock.v554;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackRequest;
import com.nukkitx.protocol.bedrock.data.inventory.TextProcessingEventOrigin;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.*;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.v534.BedrockPacketHelper_v534;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v554 extends BedrockPacketHelper_v534 {

    public static final BedrockPacketHelper_v554 INSTANCE = new BedrockPacketHelper_v554();

    private static final TextProcessingEventOrigin[] ORIGINS = TextProcessingEventOrigin.values();
    protected static final ItemDescriptorType[] TYPES = ItemDescriptorType.values();

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(442, SoundEvent.ENCHANTING_TABLE_USE);
        this.addSoundEvent(443, SoundEvent.UNDEFINED);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer, BedrockSession session) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = this.getStackRequestActionTypeFromId(byteBuf.readByte());
            return readRequestActionData(byteBuf, type, session);
        });
        List<String> filteredStrings = new ArrayList<>();
        this.readArray(buffer, filteredStrings, this::readString);

        int originVal = buffer.readIntLE();
        TextProcessingEventOrigin origin = originVal == -1 ? null : ORIGINS[originVal]; // new for v552
        return new ItemStackRequest(requestId, actions.toArray(new StackRequestActionData[0]), filteredStrings.toArray(new String[0]), origin);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, BedrockSession session, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            StackRequestActionType type = action.getType();
            byteBuf.writeByte(this.getIdFromStackRequestActionType(type));
            writeRequestActionData(byteBuf, action, session);
        });
        this.writeArray(buffer, request.getFilterStrings(), this::writeString);
        TextProcessingEventOrigin origin = request.getTextProcessingEventOrigin();
        buffer.writeIntLE(origin == null ? -1 : request.getTextProcessingEventOrigin().ordinal()); // new for v552
    }

    @Override
    protected ItemDescriptorWithCount readIngredient(ByteBuf buffer, BedrockPacketHelper helper) {
        ItemDescriptorType type = TYPES[buffer.readUnsignedByte()];

        ItemDescriptor descriptor;
        switch (type) {
            case DEFAULT:
                int itemId = buffer.readShortLE();
                int auxValue = itemId != 0 ? buffer.readShortLE() : 0;
                descriptor = new DefaultDescriptor(itemId, auxValue);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(helper.readString(buffer), buffer.readUnsignedByte());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(helper.readString(buffer));
                break;
            case DEFERRED:
                descriptor = new DeferredDescriptor(helper.readString(buffer), buffer.readUnsignedShortLE());
                break;
            default:
                descriptor = InvalidDescriptor.INSTANCE;
                break;
        }

        return new ItemDescriptorWithCount(descriptor, VarInts.readInt(buffer));
    }

    @Override
    protected void writeIngredient(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        ItemDescriptorType type = ingredient.getDescriptor().getType();
        buffer.writeByte(type.ordinal());

        switch (type) {
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) ingredient.getDescriptor();
                buffer.writeShortLE(defaultDescriptor.getItemId());
                if (defaultDescriptor.getItemId() != 0) {
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
