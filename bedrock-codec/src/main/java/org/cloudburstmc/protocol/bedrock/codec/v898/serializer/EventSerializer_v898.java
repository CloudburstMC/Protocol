package org.cloudburstmc.protocol.bedrock.codec.v898.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.EventSerializer_v685;
import org.cloudburstmc.protocol.bedrock.data.BlockInteractionType;
import org.cloudburstmc.protocol.bedrock.data.event.AchievementAwardedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.BellUsedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.CauldronInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.CauldronUsedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.ComposterInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.EntityInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventDataType;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.function.BiFunction;

public class EventSerializer_v898 extends EventSerializer_v685 {

    public static final EventSerializer_v898 INSTANCE = new EventSerializer_v898();

    private static final BlockInteractionType[] BLOCK_INTERACTION_TYPES = BlockInteractionType.values();

    public EventSerializer_v898() {
        super();
        this.readers.put(EventDataType.SLASH_COMMAND_EXECUTED, this::readSlashCommandExecuted);
        this.writers.put(EventDataType.SLASH_COMMAND_EXECUTED, this::writeSlashCommandExecuted);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EventPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        EventData eventData = packet.getEventData();
        if (eventData.getPayloadType() == -1) {
            throw new UnsupportedOperationException("Deprecated event type " + eventData.getType());
        }
        VarInts.writeInt(buffer, eventData.getType().ordinal());
        buffer.writeBoolean(packet.isUsePlayerId());

        TriConsumer<ByteBuf, BedrockCodecHelper, EventData> function = this.writers.get(eventData.getType());

        if (function == null) {
            throw new UnsupportedOperationException("Unknown event type " + eventData.getType());
        }

        VarInts.writeUnsignedInt(buffer, eventData.getPayloadType());

        function.accept(buffer, helper, eventData);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EventPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));

        int eventId = VarInts.readInt(buffer);
        Preconditions.checkElementIndex(eventId, VALUES.length, "EventDataType");
        EventDataType type = VALUES[eventId];

        packet.setUsePlayerId(buffer.readBoolean());

        VarInts.readUnsignedInt(buffer); //oneOf

        BiFunction<ByteBuf, BedrockCodecHelper, EventData> function = this.readers.get(type);

        if (function == null) {
            throw new UnsupportedOperationException("Unknown event type " + type);
        }

        packet.setEventData(function.apply(buffer, helper));
    }

    @Override
    protected AchievementAwardedEventData readAchievementAwarded(ByteBuf buffer, BedrockCodecHelper helper) {
        return new AchievementAwardedEventData(buffer.readUnsignedByte());
    }

    @Override
    protected void writeAchievementAwarded(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        AchievementAwardedEventData event = (AchievementAwardedEventData) eventData;
        buffer.writeByte(event.getAchievementId());
    }

    @Override
    protected EntityInteractEventData readEntityInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        long interactedEntityID = VarInts.readLong(buffer);
        int interactionType = buffer.readUnsignedByte();
        int interactionEntityType = VarInts.readInt(buffer);
        int entityVariant = VarInts.readInt(buffer);
        int entityColor = buffer.readUnsignedByte();
        return new EntityInteractEventData(interactedEntityID, interactionType, interactionEntityType, entityVariant, entityColor);
    }

    @Override
    protected void writeEntityInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        EntityInteractEventData event = (EntityInteractEventData) eventData;
        VarInts.writeLong(buffer, event.getInteractedEntityID());
        buffer.writeByte(event.getInteractionType());
        VarInts.writeInt(buffer, event.getLegacyEntityTypeId());
        VarInts.writeInt(buffer, event.getVariant());
        buffer.writeByte(event.getPaletteColor());
    }

    @Override
    protected CauldronUsedEventData readCauldronUsed(ByteBuf buffer, BedrockCodecHelper helper) {
        int color = VarInts.readUnsignedInt(buffer);
        int potionId = buffer.readShortLE();
        int fillLevel = buffer.readShortLE();
        return new CauldronUsedEventData(potionId, color, fillLevel);
    }

    @Override
    protected void writeCauldronUsed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CauldronUsedEventData event = (CauldronUsedEventData) eventData;
        VarInts.writeUnsignedInt(buffer, event.getColor());
        buffer.writeShortLE(event.getPotionId());
        buffer.writeShortLE(event.getFillLevel());
    }

    @Override
    protected CauldronInteractEventData readCauldronInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockInteractionType type = BLOCK_INTERACTION_TYPES[buffer.readUnsignedByte()];
        int itemId = buffer.readShortLE();
        return new CauldronInteractEventData(type, itemId);
    }

    @Override
    protected void writeCauldronInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CauldronInteractEventData event = (CauldronInteractEventData) eventData;
        buffer.writeByte(event.getBlockInteractionType().ordinal());
        buffer.writeShortLE(event.getItemId());
    }

    @Override
    protected ComposterInteractEventData readComposterInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockInteractionType type = BLOCK_INTERACTION_TYPES[buffer.readUnsignedByte()];
        int itemId = buffer.readShortLE();
        return new ComposterInteractEventData(type, itemId);
    }

    @Override
    protected void writeComposterInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        ComposterInteractEventData event = (ComposterInteractEventData) eventData;
        buffer.writeByte(event.getBlockInteractionType().ordinal());
        buffer.writeShortLE(event.getItemId());
    }

    @Override
    protected BellUsedEventData readBellUsed(ByteBuf buffer, BedrockCodecHelper helper) {
        return new BellUsedEventData(buffer.readShortLE());
    }

    @Override
    protected void writeBellUsed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        BellUsedEventData event = (BellUsedEventData) eventData;
        buffer.writeShortLE(event.getItemId());
    }
}
