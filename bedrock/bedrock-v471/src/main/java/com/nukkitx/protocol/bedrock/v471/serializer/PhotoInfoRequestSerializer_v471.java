package com.nukkitx.protocol.bedrock.v471.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PhotoInfoRequestPacket;
import io.netty.buffer.ByteBuf;

public class PhotoInfoRequestSerializer_v471 implements BedrockPacketSerializer<PhotoInfoRequestPacket> {
    public static final PhotoInfoRequestSerializer_v471 INSTANCE = new PhotoInfoRequestSerializer_v471();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoInfoRequestPacket packet) {
        VarInts.writeLong(buffer, packet.getPhotoId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoInfoRequestPacket packet) {
        packet.setPhotoId(VarInts.readLong(buffer));
    }
}
