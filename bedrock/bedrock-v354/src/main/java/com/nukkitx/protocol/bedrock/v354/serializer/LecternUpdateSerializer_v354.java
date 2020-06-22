package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LecternUpdatePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LecternUpdateSerializer_v354 implements BedrockPacketSerializer<LecternUpdatePacket> {
    public static final LecternUpdateSerializer_v354 INSTANCE = new LecternUpdateSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LecternUpdatePacket packet) {
        buffer.writeByte(packet.getPage());
        buffer.writeByte(packet.getTotalPages());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        buffer.writeBoolean(packet.isDroppingBook());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LecternUpdatePacket packet) {
        packet.setPage(buffer.readUnsignedByte());
        packet.setTotalPages(buffer.readUnsignedByte());
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setDroppingBook(buffer.readBoolean());
    }
}
