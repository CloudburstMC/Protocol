package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PacketHeaderSerializerCompat implements PacketSerializer<PacketHeader> {
    public static final PacketHeaderSerializerCompat INSTANCE = new PacketHeaderSerializerCompat();

    @Override
    public void serialize(ByteBuf buffer, PacketHeader header) {
        buffer.writeByte(header.getClientId());
    }

    @Override
    public void deserialize(ByteBuf buffer, PacketHeader header) {
        header.setPacketId(buffer.readByte());
    }
}
