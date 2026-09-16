package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.PlaySoundSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class PlaySoundSerializer_v2193 extends PlaySoundSerializer_v291 { // v291 intentional

    public static final PlaySoundSerializer_v2193 INSTANCE = new PlaySoundSerializer_v2193();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeUnsignedInt(buffer, packet.getLoopCount());
        buffer.writeBoolean(packet.isBypassListenerRangeCheck());
        helper.writeOptionalNull(buffer, packet.getServerSoundHandle(), ByteBuf::writeLongLE);
        helper.writeOptionalNull(buffer, packet.getPlaybackPositionSeconds(), ByteBuf::writeFloatLE);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setLoopCount(VarInts.readUnsignedInt(buffer));
        packet.setBypassListenerRangeCheck(buffer.readBoolean());
        packet.setServerSoundHandle(helper.readOptional(buffer, null, ByteBuf::readLongLE));
        packet.setPlaybackPositionSeconds(helper.readOptional(buffer, null, ByteBuf::readFloatLE));
    }
}
