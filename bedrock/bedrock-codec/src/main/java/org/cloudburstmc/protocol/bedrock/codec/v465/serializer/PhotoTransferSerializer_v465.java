package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.PhotoTransferSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.PhotoType;
import org.cloudburstmc.protocol.bedrock.packet.PhotoTransferPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoTransferSerializer_v465 extends PhotoTransferSerializer_v291 {
    public static final PhotoTransferSerializer_v465 INSTANCE = new PhotoTransferSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PhotoTransferPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getPhotoType().ordinal());
        buffer.writeByte(packet.getSourceType().ordinal());
        buffer.writeLongLE(packet.getOwnerId());
        helper.writeString(buffer, packet.getNewPhotoName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PhotoTransferPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setPhotoType(PhotoType.from(buffer.readByte()));
        packet.setSourceType(PhotoType.from(buffer.readByte()));
        packet.setOwnerId(buffer.readLongLE());
        packet.setNewPhotoName(helper.readString(buffer));
    }
}
