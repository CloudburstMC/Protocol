package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockEventSerializer_v361 implements PacketSerializer<BlockEventPacket> {
    public static final BlockEventSerializer_v361 INSTANCE = new BlockEventSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BlockEventPacket packet) {
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getEventType());
        VarInts.writeInt(buffer, packet.getEventData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BlockEventPacket packet) {
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setEventType(VarInts.readInt(buffer));
        packet.setEventData(VarInts.readInt(buffer));
    }
}
