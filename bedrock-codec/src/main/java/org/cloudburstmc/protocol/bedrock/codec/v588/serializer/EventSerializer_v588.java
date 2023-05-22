package org.cloudburstmc.protocol.bedrock.codec.v588.serializer;

import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.EventSerializer_v471;
import org.cloudburstmc.protocol.bedrock.data.event.CarefulRestorationEventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventDataType;

public class EventSerializer_v588 extends EventSerializer_v471 {
    public static final EventSerializer_v588 INSTANCE = new EventSerializer_v588();

    public EventSerializer_v588() {
        super();
        this.readers.put(EventDataType.CAREFUL_RESTORATION, (b, h) -> CarefulRestorationEventData.INSTANCE);
        this.writers.put(EventDataType.CAREFUL_RESTORATION, (b, h, e) -> {});
    }
}
