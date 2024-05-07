package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.EventSerializer_v589;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventDataType;
import org.cloudburstmc.protocol.bedrock.data.event.ItemUsedEventData;

public class EventSerializer_v685 extends EventSerializer_v589 {
    public static final EventSerializer_v685 INSTANCE = new EventSerializer_v685();

    public EventSerializer_v685() {
        super();
        this.readers.put(EventDataType.ITEM_USED_EVENT, this::readItemUsed);
        this.writers.put(EventDataType.ITEM_USED_EVENT, this::writeItemUsed);
    }

    protected ItemUsedEventData readItemUsed(ByteBuf buffer, BedrockCodecHelper helper) {
        short itemId = buffer.readShortLE();
        int itemAux = buffer.readIntLE();
        int useMethod = buffer.readIntLE();
        int useCount = buffer.readIntLE();
        return new ItemUsedEventData(itemId, itemAux, useMethod, useCount);
    }

    protected void writeItemUsed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        ItemUsedEventData event = (ItemUsedEventData) eventData;
        buffer.writeShortLE(event.getItemId());
        buffer.writeIntLE(event.getItemAux());
        buffer.writeIntLE(event.getUseMethod());
        buffer.writeIntLE(event.getUseCount());
    }
}