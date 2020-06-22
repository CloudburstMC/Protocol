package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FullChunkDataSerializer_v291 implements BedrockPacketSerializer<LevelChunkPacket> {
    public static final FullChunkDataSerializer_v291 INSTANCE = new FullChunkDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeInt(buffer, packet.getChunkZ());
        helper.writeByteArray(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));
        packet.setData(helper.readByteArray(buffer));
    }
}
