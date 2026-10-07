package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.sound.StopSoundData;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundUpdateSoundDataPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundUpdateSoundDataSerializer_v1001 implements BedrockPacketSerializer<ClientboundUpdateSoundDataPacket> {

    public static final ClientboundUpdateSoundDataSerializer_v1001 INSTANCE = new ClientboundUpdateSoundDataSerializer_v1001();

    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        buffer.writeLongLE(packet.getServerSoundHandle());
        helper.writeString(buffer, "stop");
    }

    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        packet.setServerSoundHandle(buffer.readLongLE());
        helper.readString(buffer); // unused
        packet.setEvent(new StopSoundData());
    }
}
