package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v944.serializer.PartyChangedSerializer_v944;
import org.cloudburstmc.protocol.bedrock.packet.PartyChangedPacket;

public class PartyChangedSerializer_v975 extends PartyChangedSerializer_v944 {

    public static final PartyChangedSerializer_v975 INSTANCE = new PartyChangedSerializer_v975();

    @Override
    protected void writeParty(ByteBuf buffer, BedrockCodecHelper helper, PartyChangedPacket.PartyInfo info) {
        helper.writeString(buffer, info.getPartyId());
        buffer.writeBoolean(info.isPartyLeader());
    }

    @Override
    protected PartyChangedPacket.PartyInfo readParty(ByteBuf buffer, BedrockCodecHelper helper) {
        return new PartyChangedPacket.PartyInfo(helper.readStringMaxLen(buffer, 49), buffer.readBoolean());
    }
}
