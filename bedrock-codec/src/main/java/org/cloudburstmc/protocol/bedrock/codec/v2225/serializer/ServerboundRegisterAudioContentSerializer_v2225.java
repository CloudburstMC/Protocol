package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundRegisterAudioContentPacket;

public class ServerboundRegisterAudioContentSerializer_v2225 implements BedrockPacketSerializer<ServerboundRegisterAudioContentPacket> {

    public static final ServerboundRegisterAudioContentSerializer_v2225 INSTANCE = new ServerboundRegisterAudioContentSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundRegisterAudioContentPacket packet) {
        helper.writeArray(buffer, packet.getRegistrations(), (buf, r) -> {
            helper.writeString(buf, r.getAudioContentID());
            helper.writeString(buf, r.getSharedMetadata());
            helper.writeString(buf, r.getServerContent());
            helper.writeString(buf, r.getPlaybackContent());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundRegisterAudioContentPacket packet) {
        helper.readArray(buffer, packet.getRegistrations(), (buf, h) ->
                new ServerboundRegisterAudioContentPacket.AudioContentRegistrationEntry(
                helper.readString(buf),
                helper.readString(buf),
                helper.readString(buf),
                helper.readString(buf)));
    }
}
