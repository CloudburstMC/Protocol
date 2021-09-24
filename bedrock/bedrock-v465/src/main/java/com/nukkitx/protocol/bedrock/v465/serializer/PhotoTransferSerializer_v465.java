package com.nukkitx.protocol.bedrock.v465.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.PhotoType;
import com.nukkitx.protocol.bedrock.packet.PhotoTransferPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.PhotoTransferSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoTransferSerializer_v465 extends PhotoTransferSerializer_v291 {

    public static final PhotoTransferSerializer_v465 INSTANCE = new PhotoTransferSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getPhotoType().ordinal());
        buffer.writeByte(packet.getSourceType().ordinal());
        buffer.writeLongLE(packet.getOwnerId());
        helper.writeString(buffer, packet.getNewPhotoName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setPhotoType(PhotoType.from(buffer.readByte()));
        packet.setSourceType(PhotoType.from(buffer.readByte()));
        packet.setOwnerId(buffer.readLongLE());
        packet.setNewPhotoName(helper.readString(buffer));
    }
}
