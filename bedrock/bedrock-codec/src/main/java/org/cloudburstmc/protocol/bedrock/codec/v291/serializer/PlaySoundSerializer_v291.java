package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaySoundSerializer_v291 implements BedrockPacketSerializer<PlaySoundPacket> {
    public static final PlaySoundSerializer_v291 INSTANCE = new PlaySoundSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        helper.writeString(buffer, packet.getSound());
        helper.writeBlockPosition(buffer, packet.getPosition().mul(8).toInt());
        buffer.writeFloatLE(packet.getVolume());
        buffer.writeFloatLE(packet.getPitch());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        packet.setSound(helper.readString(buffer));
        packet.setPosition(helper.readBlockPosition(buffer).toFloat().div(8));
        packet.setVolume(buffer.readFloatLE());
        packet.setPitch(buffer.readFloatLE());
    }
}
