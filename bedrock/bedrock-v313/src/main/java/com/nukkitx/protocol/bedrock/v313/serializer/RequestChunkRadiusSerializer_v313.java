package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestChunkRadiusSerializer_v313 implements PacketSerializer<RequestChunkRadiusPacket> {
    public static final RequestChunkRadiusSerializer_v313 INSTANCE = new RequestChunkRadiusSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, RequestChunkRadiusPacket packet) {
        VarInts.writeInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, RequestChunkRadiusPacket packet) {
        packet.setRadius(VarInts.readInt(buffer));
    }
}
