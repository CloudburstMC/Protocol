package com.nukkitx.protocol.bedrock.v465.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CreatePhotoPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePhotoSerializer_v465 implements BedrockPacketSerializer<CreatePhotoPacket> {

    public static final CreatePhotoSerializer_v465 INSTANCE = new CreatePhotoSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CreatePhotoPacket packet) {
        buffer.writeLongLE(packet.getId());
        helper.writeString(buffer, packet.getPhotoName());
        helper.writeString(buffer, packet.getPhotoItemName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CreatePhotoPacket packet) {
        packet.setId(buffer.readLongLE());
        packet.setPhotoName(helper.readString(buffer));
        packet.setPhotoItemName(helper.readString(buffer));
    }
}
