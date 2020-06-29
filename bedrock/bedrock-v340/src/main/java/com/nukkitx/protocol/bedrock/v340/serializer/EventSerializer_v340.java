package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.event.EventData;
import com.nukkitx.protocol.bedrock.data.event.PetDiedEventData;
import com.nukkitx.protocol.bedrock.v332.serializer.EventSerializer_v332;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventSerializer_v340 extends EventSerializer_v332 {
    public static final EventSerializer_v340 INSTANCE = new EventSerializer_v340();

    @Override
    protected PetDiedEventData readPetDied(ByteBuf buffer, BedrockPacketHelper helper) {
        boolean killedByOwner = buffer.readBoolean();
        long killerUniqueEntityId = VarInts.readLong(buffer);
        long petUniqueEntityId = VarInts.readLong(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        int petEntityType = VarInts.readInt(buffer);

        return new PetDiedEventData(killedByOwner, killerUniqueEntityId, petUniqueEntityId, entityDamageCause, petEntityType);
    }

    @Override
    protected void writePetDied(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        super.writePetDied(buffer, helper, eventData);

        VarInts.writeInt(buffer, ((PetDiedEventData) eventData).getPetEntityType());
    }
}
