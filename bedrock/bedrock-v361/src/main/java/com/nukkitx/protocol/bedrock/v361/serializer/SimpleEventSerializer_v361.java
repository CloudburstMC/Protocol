package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.packet.SimpleEventPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleEventSerializer_v361 implements PacketSerializer<SimpleEventPacket> {
    public static final SimpleEventSerializer_v361 INSTANCE = new SimpleEventSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, SimpleEventPacket packet) {
        buffer.writeShortLE(packet.getEvent());
    }

    @Override
    public void deserialize(ByteBuf buffer, SimpleEventPacket packet) {
        packet.setEvent(buffer.readShortLE());
    }
}
