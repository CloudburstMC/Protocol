package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.BlockInteractionType;
import com.nukkitx.protocol.bedrock.data.event.BellUsedEventData;
import com.nukkitx.protocol.bedrock.data.event.CauldronInteractEventData;
import com.nukkitx.protocol.bedrock.data.event.ComposterInteractEventData;
import com.nukkitx.protocol.bedrock.data.event.EventData;
import com.nukkitx.protocol.bedrock.v340.serializer.EventSerializer_v340;
import io.netty.buffer.ByteBuf;

import static com.nukkitx.protocol.bedrock.data.event.EventDataType.*;

public class EventSerializer_v354 extends EventSerializer_v340 {
    public static final EventSerializer_v354 INSTANCE = new EventSerializer_v354();

    protected EventSerializer_v354() {
        super();
        this.readers.put(CAULDRON_INTERACT, this::readCauldronInteract);
        this.readers.put(COMPOSTER_INTERACT, this::readComposterInteract);
        this.readers.put(BELL_USED, this::readBellUsed);
        this.writers.put(CAULDRON_INTERACT, this::writeCauldronInteract);
        this.writers.put(COMPOSTER_INTERACT, this::writeComposterInteract);
        this.writers.put(BELL_USED, this::writeBellUsed);
    }

    protected CauldronInteractEventData readCauldronInteract(ByteBuf buffer, BedrockPacketHelper helper) {
        BlockInteractionType type = BlockInteractionType.values()[VarInts.readInt(buffer)];
        int itemId = VarInts.readInt(buffer);
        return new CauldronInteractEventData(type, itemId);
    }

    protected void writeCauldronInteract(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        CauldronInteractEventData event = (CauldronInteractEventData) eventData;
        VarInts.writeInt(buffer, event.getBlockInteractionType().ordinal());
        VarInts.writeInt(buffer, event.getItemId());
    }

    protected ComposterInteractEventData readComposterInteract(ByteBuf buffer, BedrockPacketHelper helper) {
        BlockInteractionType type = BlockInteractionType.values()[VarInts.readInt(buffer)];
        int itemId = VarInts.readInt(buffer);
        return new ComposterInteractEventData(type, itemId);
    }

    protected void writeComposterInteract(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        ComposterInteractEventData event = (ComposterInteractEventData) eventData;
        VarInts.writeInt(buffer, event.getBlockInteractionType().ordinal());
        VarInts.writeInt(buffer, event.getItemId());
    }

    protected BellUsedEventData readBellUsed(ByteBuf buffer, BedrockPacketHelper helper) {
        int itemId = VarInts.readInt(buffer);
        return new BellUsedEventData(itemId);
    }

    protected void writeBellUsed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        BellUsedEventData event = (BellUsedEventData) eventData;
        VarInts.writeInt(buffer, event.getItemId());
    }
}
