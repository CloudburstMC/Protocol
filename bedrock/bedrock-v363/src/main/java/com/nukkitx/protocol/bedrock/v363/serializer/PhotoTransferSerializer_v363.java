package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PhotoTransferPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoTransferSerializer_v363 implements PacketSerializer<PhotoTransferPacket> {
    public static final PhotoTransferSerializer_v363 INSTANCE = new PhotoTransferSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, PhotoTransferPacket packet) {
        BedrockUtils.writeString(buffer, packet.getName());
        byte[] data = packet.getData();
        VarInts.writeUnsignedInt(buffer, data.length);
        buffer.writeBytes(data);
        BedrockUtils.writeString(buffer, packet.getBookId());
    }

    @Override
    public void deserialize(ByteBuf buffer, PhotoTransferPacket packet) {
        packet.setName(BedrockUtils.readString(buffer));
        byte[] data = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(data);
        packet.setData(data);
        packet.setBookId(BedrockUtils.readString(buffer));
    }
}
