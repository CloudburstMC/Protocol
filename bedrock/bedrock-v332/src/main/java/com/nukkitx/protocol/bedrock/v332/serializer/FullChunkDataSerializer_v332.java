package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.FullChunkDataPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullChunkDataSerializer_v332 implements PacketSerializer<FullChunkDataPacket> {
    public static final FullChunkDataSerializer_v332 INSTANCE = new FullChunkDataSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, FullChunkDataPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeInt(buffer, packet.getChunkZ());
        byte[] data = packet.getData();
        VarInts.writeUnsignedInt(buffer, data.length);
        buffer.writeBytes(data);
    }

    @Override
    public void deserialize(ByteBuf buffer, FullChunkDataPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));
        byte[] data = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(data);
        packet.setData(data);
    }
}
