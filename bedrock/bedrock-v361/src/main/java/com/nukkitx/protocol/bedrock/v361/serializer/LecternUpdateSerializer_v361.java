package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.packet.LecternUpdatePacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LecternUpdateSerializer_v361 implements PacketSerializer<LecternUpdatePacket> {
    public static final LecternUpdateSerializer_v361 INSTANCE = new LecternUpdateSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, LecternUpdatePacket packet) {
        buffer.writeByte(packet.getPage());
        buffer.writeByte(packet.getTotalPages());
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        buffer.writeBoolean(packet.isDroppingBook());
    }

    @Override
    public void deserialize(ByteBuf buffer, LecternUpdatePacket packet) {
        packet.setPage(buffer.readUnsignedByte());
        packet.setTotalPages(buffer.readUnsignedByte());
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setDroppingBook(buffer.readBoolean());
    }
}
