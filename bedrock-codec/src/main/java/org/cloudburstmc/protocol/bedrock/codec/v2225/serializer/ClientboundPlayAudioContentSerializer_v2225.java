package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.PlaySoundSerializer_v2193;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundPlayAudioContentPacket;

public class ClientboundPlayAudioContentSerializer_v2225 implements BedrockPacketSerializer<ClientboundPlayAudioContentPacket> {

    public static final ClientboundPlayAudioContentSerializer_v2225 INSTANCE = new ClientboundPlayAudioContentSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundPlayAudioContentPacket packet) {
        helper.writeString(buffer, packet.getSharedMetadata());
        helper.writeString(buffer, packet.getPlaybackContent());
        helper.writeString(buffer, packet.getPlaybackType().getName());
        PlaySoundSerializer_v2193.INSTANCE.serialize(buffer, helper, packet.getPlaySound());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundPlayAudioContentPacket packet) {
        packet.setSharedMetadata(helper.readString(buffer));
        packet.setPlaybackContent(helper.readString(buffer));
        packet.setPlaybackType(ClientboundPlayAudioContentPacket.AudioContentPlaybackType.fromName(helper.readString(buffer)));
        PlaySoundSerializer_v2193.INSTANCE.deserialize(buffer, helper, packet.getPlaySound());
    }
}
