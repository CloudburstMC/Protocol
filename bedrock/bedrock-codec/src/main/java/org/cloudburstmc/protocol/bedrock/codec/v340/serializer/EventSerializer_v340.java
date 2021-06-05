package org.cloudburstmc.protocol.bedrock.codec.v340.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.EventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.bedrock.data.event.PetDiedEventData;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventSerializer_v340 extends EventSerializer_v332 {
    public static final EventSerializer_v340 INSTANCE = new EventSerializer_v340();

    @Override
    protected PetDiedEventData readPetDied(ByteBuf buffer, BedrockCodecHelper helper) {
        boolean killedByOwner = buffer.readBoolean();
        long killerUniqueEntityId = VarInts.readLong(buffer);
        long petUniqueEntityId = VarInts.readLong(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        int petEntityType = VarInts.readInt(buffer);

        return new PetDiedEventData(killedByOwner, killerUniqueEntityId, petUniqueEntityId, entityDamageCause, petEntityType);
    }

    @Override
    protected void writePetDied(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        super.writePetDied(buffer, helper, eventData);

        VarInts.writeInt(buffer, ((PetDiedEventData) eventData).getPetEntityType());
    }
}
