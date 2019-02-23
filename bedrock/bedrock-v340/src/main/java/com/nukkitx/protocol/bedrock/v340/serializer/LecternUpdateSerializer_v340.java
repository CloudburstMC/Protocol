package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.protocol.bedrock.packet.LecternUpdatePacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LecternUpdateSerializer_v340 implements PacketSerializer<LecternUpdatePacket> {
    public static final LecternUpdateSerializer_v340 INSTANCE = new LecternUpdateSerializer_v340();

    @Override
    public void serialize(ByteBuf buffer, LecternUpdatePacket packet) {
        buffer.writeByte(packet.getPage());
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        buffer.writeBoolean(packet.isUnknownBool());
    }

    @Override
    public void deserialize(ByteBuf buffer, LecternUpdatePacket packet) {
        packet.setPage(buffer.readUnsignedByte());
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setUnknownBool(buffer.readBoolean());
    }
}
