package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.MapCreateLockedCopyPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MapCreateLockedCopySerializer_v354 implements BedrockPacketSerializer<MapCreateLockedCopyPacket> {
    public static final MapCreateLockedCopySerializer_v354 INSTANCE = new MapCreateLockedCopySerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MapCreateLockedCopyPacket packet) {
        VarInts.writeLong(buffer, packet.getOriginalMapId());
        VarInts.writeLong(buffer, packet.getNewMapId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MapCreateLockedCopyPacket packet) {
        packet.setOriginalMapId(VarInts.readLong(buffer));
        packet.setNewMapId(VarInts.readLong(buffer));
    }
}
