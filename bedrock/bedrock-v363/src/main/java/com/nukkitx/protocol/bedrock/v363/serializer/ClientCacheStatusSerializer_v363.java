package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.ClientCacheStatusPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientCacheStatusSerializer_v363 implements PacketSerializer<ClientCacheStatusPacket> {
    public static final ClientCacheStatusSerializer_v363 INSTANCE = new ClientCacheStatusSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, ClientCacheStatusPacket packet) {
        buffer.writeBoolean(packet.isSupported());
    }

    @Override
    public void deserialize(ByteBuf buffer, ClientCacheStatusPacket packet) {
        packet.setSupported(buffer.readBoolean());
    }
}
