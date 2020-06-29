package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PhotoTransferPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoTransferSerializer_v291 implements BedrockPacketSerializer<PhotoTransferPacket> {
    public static final PhotoTransferSerializer_v291 INSTANCE = new PhotoTransferSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
        helper.writeString(buffer, packet.getName());
        byte[] data = packet.getData();
        VarInts.writeUnsignedInt(buffer, data.length);
        buffer.writeBytes(data);
        helper.writeString(buffer, packet.getBookId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
        packet.setName(helper.readString(buffer));
        byte[] data = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(data);
        packet.setData(data);
        packet.setBookId(helper.readString(buffer));
    }
}
