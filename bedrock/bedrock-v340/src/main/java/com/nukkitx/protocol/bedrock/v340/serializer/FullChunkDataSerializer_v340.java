package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullChunkDataSerializer_v340 implements PacketSerializer<LevelChunkPacket> {
    public static final FullChunkDataSerializer_v340 INSTANCE = new FullChunkDataSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, LevelChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeInt(buffer, packet.getChunkZ());
        byte[] data = packet.getData();
        VarInts.writeUnsignedInt(buffer, data.length);
        buffer.writeBytes(data);
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelChunkPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));
        byte[] data = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(data);
        packet.setData(data);
    }
}
