package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ChunkRadiusUpdatedPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChunkRadiusUpdatedSerializer_v291 implements BedrockPacketSerializer<ChunkRadiusUpdatedPacket> {
    public static final ChunkRadiusUpdatedSerializer_v291 INSTANCE = new ChunkRadiusUpdatedSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ChunkRadiusUpdatedPacket packet) {
        VarInts.writeInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ChunkRadiusUpdatedPacket packet) {
        packet.setRadius(VarInts.readInt(buffer));
    }
}
