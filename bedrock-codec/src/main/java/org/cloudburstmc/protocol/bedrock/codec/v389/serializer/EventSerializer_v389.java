package org.cloudburstmc.protocol.bedrock.codec.v389.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.EventSerializer_v388;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventDataType;
import org.cloudburstmc.protocol.bedrock.data.event.ExtractHoneyEventData;
import org.cloudburstmc.protocol.bedrock.data.event.PlayerDiedEventData;
import org.cloudburstmc.protocol.common.util.VarInts;

public class EventSerializer_v389 extends EventSerializer_v388 {
    public static final EventSerializer_v389 INSTANCE = new EventSerializer_v389();

    protected EventSerializer_v389() {
        super();
        this.readers.put(EventDataType.EXTRACT_HONEY, (b, h) -> ExtractHoneyEventData.INSTANCE);
        this.writers.put(EventDataType.EXTRACT_HONEY, (b, h, e) -> {});
        this.writers.put(EventDataType.PLAYER_DIED,this::writePlayerDied);
        this.readers.put(EventDataType.PLAYER_DIED,this::readPlayerDied);
    }

    @Override
    protected PlayerDiedEventData readPlayerDied(ByteBuf buffer, BedrockCodecHelper helper) {
        int attackerEntityId = VarInts.readInt(buffer);
        int attackerVariant = VarInts.readInt(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        boolean inRaid = buffer.readBoolean();
        return new PlayerDiedEventData(attackerEntityId, attackerVariant, entityDamageCause, inRaid);
    }

    @Override
    protected void writePlayerDied(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        PlayerDiedEventData event = (PlayerDiedEventData) eventData;
        VarInts.writeInt(buffer, event.getAttackerEntityId());
        VarInts.writeInt(buffer, event.getAttackerVariant());
        VarInts.writeInt(buffer, event.getEntityDamageCause());
        buffer.writeBoolean(event.isInRaid());
    }
}
