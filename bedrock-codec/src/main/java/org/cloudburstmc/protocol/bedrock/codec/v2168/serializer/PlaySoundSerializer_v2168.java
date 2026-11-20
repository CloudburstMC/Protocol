package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.PlaySoundSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class PlaySoundSerializer_v2168 extends PlaySoundSerializer_v291 { // v291 intentional

    public static final PlaySoundSerializer_v2168 INSTANCE = new PlaySoundSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeInt(buffer, packet.getLoopCount());
        helper.writeOptionalNull(buffer, packet.getServerSoundHandle(), ByteBuf::writeLongLE);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setLoopCount(VarInts.readInt(buffer));
        packet.setServerSoundHandle(helper.readOptional(buffer, null, ByteBuf::readLongLE));
    }
}
