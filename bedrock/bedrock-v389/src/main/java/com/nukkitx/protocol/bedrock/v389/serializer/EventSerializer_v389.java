package com.nukkitx.protocol.bedrock.v389.serializer;

import com.nukkitx.protocol.bedrock.data.event.ExtractHoneyEventData;
import com.nukkitx.protocol.bedrock.v388.serializer.EventSerializer_v388;

import static com.nukkitx.protocol.bedrock.data.event.EventDataType.*;

public class EventSerializer_v389 extends EventSerializer_v388 {
    public static final EventSerializer_v389 INSTANCE = new EventSerializer_v389();

    protected EventSerializer_v389() {
        super();
        this.readers.put(EXTRACT_HONEY, (b, h) -> ExtractHoneyEventData.INSTANCE);
        this.writers.put(EXTRACT_HONEY, (b, h, e) -> {});
    }
}
