package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.MapInfoRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MapInfoRequestSerializer_v291 implements BedrockPacketSerializer<MapInfoRequestPacket> {
    public static final MapInfoRequestSerializer_v291 INSTANCE = new MapInfoRequestSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MapInfoRequestPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueMapId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MapInfoRequestPacket packet) {
        packet.setUniqueMapId(VarInts.readLong(buffer));
    }
}
