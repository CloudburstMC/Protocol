package org.cloudburstmc.protocol.bedrock.codec.v471.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PhotoInfoRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoInfoRequestSerializer_v471 implements BedrockPacketSerializer<PhotoInfoRequestPacket> {

    public static final PhotoInfoRequestSerializer_v471 INSTANCE = new PhotoInfoRequestSerializer_v471();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PhotoInfoRequestPacket packet) {
        VarInts.writeLong(buffer, packet.getPhotoId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PhotoInfoRequestPacket packet) {
        packet.setPhotoId(VarInts.readLong(buffer));
    }
}
