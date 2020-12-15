package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.event.EventData;
import com.nukkitx.protocol.bedrock.data.event.MobBornEventData;
import com.nukkitx.protocol.bedrock.data.event.PetDiedEventData;
import com.nukkitx.protocol.bedrock.v291.serializer.EventSerializer_v291;
import io.netty.buffer.ByteBuf;

import static com.nukkitx.protocol.bedrock.data.event.EventDataType.MOB_BORN;
import static com.nukkitx.protocol.bedrock.data.event.EventDataType.PET_DIED;

public class EventSerializer_v332 extends EventSerializer_v291 {
    public static final EventSerializer_v332 INSTANCE = new EventSerializer_v332();

    protected EventSerializer_v332() {
        super();
        this.readers.put(MOB_BORN, this::readMobBorn);
        this.readers.put(PET_DIED, this::readPetDied);
        this.writers.put(MOB_BORN, this::writeMobBorn);
        this.writers.put(PET_DIED, this::writePetDied);
    }

    protected MobBornEventData readMobBorn(ByteBuf buffer, BedrockPacketHelper helper) {
        int entityType = VarInts.readInt(buffer);
        int variant = VarInts.readInt(buffer);
        int color = buffer.readUnsignedByte();
        return new MobBornEventData(entityType, variant, color);
    }

    protected void writeMobBorn(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        MobBornEventData event = (MobBornEventData) eventData;
        VarInts.writeInt(buffer, event.getEntityType());
        VarInts.writeInt(buffer, event.getVariant());
        buffer.writeByte(event.getColor());
    }

    protected PetDiedEventData readPetDied(ByteBuf buffer, BedrockPacketHelper helper) {
        boolean killedByOwner = buffer.readBoolean();
        long killerUniqueEntityId = VarInts.readLong(buffer);
        long petUniqueEntityId = VarInts.readLong(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        return new PetDiedEventData(killedByOwner, killerUniqueEntityId, petUniqueEntityId, entityDamageCause, -1);
    }

    protected void writePetDied(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        PetDiedEventData event = (PetDiedEventData) eventData;
        buffer.writeBoolean(event.isOwnerKilled());
        VarInts.writeLong(buffer, event.getKillerUniqueEntityId());
        VarInts.writeLong(buffer, event.getPetUniqueEntityId());
        VarInts.writeInt(buffer, event.getEntityDamageCause());
    }
}
