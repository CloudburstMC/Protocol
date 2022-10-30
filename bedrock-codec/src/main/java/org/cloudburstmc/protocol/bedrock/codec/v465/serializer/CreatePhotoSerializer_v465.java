package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CreatePhotoPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePhotoSerializer_v465 implements BedrockPacketSerializer<CreatePhotoPacket> {
    public static final CreatePhotoSerializer_v465 INSTANCE = new CreatePhotoSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CreatePhotoPacket packet) {
        buffer.writeLongLE(packet.getId());
        helper.writeString(buffer, packet.getPhotoName());
        helper.writeString(buffer, packet.getPhotoItemName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CreatePhotoPacket packet) {
        packet.setId(buffer.readLongLE());
        packet.setPhotoName(helper.readString(buffer));
        packet.setPhotoItemName(helper.readString(buffer));
    }
}
