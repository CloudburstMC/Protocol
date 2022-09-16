package com.nukkitx.protocol.bedrock.v554;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackRequest;
import com.nukkitx.protocol.bedrock.data.inventory.TextProcessingEventOrigin;
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
}
