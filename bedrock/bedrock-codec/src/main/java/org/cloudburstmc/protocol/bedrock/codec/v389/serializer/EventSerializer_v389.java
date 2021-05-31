package org.cloudburstmc.protocol.bedrock.codec.v389.serializer;

import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.EventSerializer_v388;
import org.cloudburstmc.protocol.bedrock.data.event.ExtractHoneyEventData;

public class EventSerializer_v389 extends EventSerializer_v388 {
    public static final EventSerializer_v389 INSTANCE = new EventSerializer_v389();

    protected EventSerializer_v389() {
        super();
        this.readers.put(EventDataType.EXTRACT_HONEY, (b, h) -> ExtractHoneyEventData.INSTANCE);
        this.writers.put(EventDataType.EXTRACT_HONEY, (b, h, e) -> {
        });
    }
}
