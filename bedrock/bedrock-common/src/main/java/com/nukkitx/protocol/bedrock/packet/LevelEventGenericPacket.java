package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LevelEventGeneric is sent by the server to send a 'generic' level event to the client. This packet sends an
 * NBT serialised object and may for that reason be used for any event holding additional data.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class LevelEventGenericPacket extends BedrockPacket {
    private int eventId;

    /**
     *  SerialisedEventData is a network little endian serialised object of event data, with fields that vary
     *  depending on eventId.
     *  Unlike many other NBT structures, this data is not actually in a compound but just loosely floating
     *  NBT tags. To decode using the nbt package, you would need to append 0x0a00 at the start (compound id
     *  and name length) and add 0x00 at the end, to manually wrap it in a compound. Likewise, you would have
     *  to remove these bytes when encoding.
     */
    private NbtMap tag;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_EVENT_GENERIC;
    }
}
