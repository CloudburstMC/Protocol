package org.cloudburstmc.protocol.bedrock.codec.v671.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.EventSerializer_v589;
import org.cloudburstmc.protocol.bedrock.data.event.EntityInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.common.util.VarInts;

public class EventSerializer_v671 extends EventSerializer_v589 {
    public static final EventSerializer_v671 INSTANCE = new EventSerializer_v671();

    @Override
    protected EntityInteractEventData readEntityInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        long interactedEntityID = VarInts.readLong(buffer);
        int interactionType = VarInts.readInt(buffer);
        int interactionEntityType = VarInts.readInt(buffer);
        int entityVariant = VarInts.readInt(buffer);
        int entityColor = buffer.readUnsignedByte();
        return new EntityInteractEventData(interactedEntityID, interactionType, interactionEntityType, entityVariant, entityColor);
    }

    @Override
    protected void writeEntityInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        EntityInteractEventData event = (EntityInteractEventData) eventData;
        VarInts.writeLong(buffer, event.getInteractedEntityID());
        VarInts.writeInt(buffer, event.getInteractionType());
        VarInts.writeInt(buffer, event.getLegacyEntityTypeId());
        VarInts.writeInt(buffer, event.getVariant());
        buffer.writeByte(event.getPaletteColor());
    }
}