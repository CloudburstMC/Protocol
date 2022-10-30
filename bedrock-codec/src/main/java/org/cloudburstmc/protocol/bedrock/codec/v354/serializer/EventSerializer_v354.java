package org.cloudburstmc.protocol.bedrock.codec.v354.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v340.serializer.EventSerializer_v340;
import org.cloudburstmc.protocol.bedrock.data.BlockInteractionType;
import org.cloudburstmc.protocol.bedrock.data.event.*;
import org.cloudburstmc.protocol.common.util.VarInts;

public class EventSerializer_v354 extends EventSerializer_v340 {
    public static final EventSerializer_v354 INSTANCE = new EventSerializer_v354();

    protected EventSerializer_v354() {
        super();
        this.readers.put(EventDataType.CAULDRON_INTERACT, this::readCauldronInteract);
        this.readers.put(EventDataType.COMPOSTER_INTERACT, this::readComposterInteract);
        this.readers.put(EventDataType.BELL_USED, this::readBellUsed);
        this.writers.put(EventDataType.CAULDRON_INTERACT, this::writeCauldronInteract);
        this.writers.put(EventDataType.COMPOSTER_INTERACT, this::writeComposterInteract);
        this.writers.put(EventDataType.BELL_USED, this::writeBellUsed);
    }

    protected CauldronInteractEventData readCauldronInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockInteractionType type = BlockInteractionType.values()[VarInts.readInt(buffer)];
        int itemId = VarInts.readInt(buffer);
        return new CauldronInteractEventData(type, itemId);
    }

    protected void writeCauldronInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CauldronInteractEventData event = (CauldronInteractEventData) eventData;
        VarInts.writeInt(buffer, event.getBlockInteractionType().ordinal());
        VarInts.writeInt(buffer, event.getItemId());
    }

    protected ComposterInteractEventData readComposterInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockInteractionType type = BlockInteractionType.values()[VarInts.readInt(buffer)];
        int itemId = VarInts.readInt(buffer);
        return new ComposterInteractEventData(type, itemId);
    }

    protected void writeComposterInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        ComposterInteractEventData event = (ComposterInteractEventData) eventData;
        VarInts.writeInt(buffer, event.getBlockInteractionType().ordinal());
        VarInts.writeInt(buffer, event.getItemId());
    }

    protected BellUsedEventData readBellUsed(ByteBuf buffer, BedrockCodecHelper helper) {
        int itemId = VarInts.readInt(buffer);
        return new BellUsedEventData(itemId);
    }

    protected void writeBellUsed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        BellUsedEventData event = (BellUsedEventData) eventData;
        VarInts.writeInt(buffer, event.getItemId());
    }
}
