package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockEventSerializer_v291 implements BedrockPacketSerializer<BlockEventPacket> {
    public static final BlockEventSerializer_v291 INSTANCE = new BlockEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, BlockEventPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getEventType());
        VarInts.writeInt(buffer, packet.getEventData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, BlockEventPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setEventType(VarInts.readInt(buffer));
        packet.setEventData(VarInts.readInt(buffer));
    }
}
