package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ClientCacheStatusPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCacheStatusSerializer_v361 implements BedrockPacketSerializer<ClientCacheStatusPacket> {
    public static final ClientCacheStatusSerializer_v361 INSTANCE = new ClientCacheStatusSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCacheStatusPacket packet) {
        buffer.writeBoolean(packet.isSupported());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCacheStatusPacket packet) {
        packet.setSupported(buffer.readBoolean());
    }
}
