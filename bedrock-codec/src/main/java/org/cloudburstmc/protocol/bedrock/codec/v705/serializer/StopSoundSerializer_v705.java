package org.cloudburstmc.protocol.bedrock.codec.v705.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.StopSoundSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.StopSoundPacket;

public class StopSoundSerializer_v705 extends StopSoundSerializer_v291 {
    public static final StopSoundSerializer_v705 INSTANCE = new StopSoundSerializer_v705();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StopSoundPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isStopMusicLegacy());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StopSoundPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setStopMusicLegacy(buffer.readBoolean());
    }
}