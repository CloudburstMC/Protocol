package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetSpawnPositionSerializer_v407 implements BedrockPacketSerializer<SetSpawnPositionPacket> {
    public static final SetSpawnPositionSerializer_v407 INSTANCE = new SetSpawnPositionSerializer_v407();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetSpawnPositionPacket packet) {
        VarInts.writeInt(buffer, packet.getSpawnType().ordinal());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getDimensionId());
        helper.writeBlockPosition(buffer, packet.getSpawnPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetSpawnPositionPacket packet) {
        packet.setSpawnType(SetSpawnPositionPacket.Type.values()[VarInts.readInt(buffer)]);
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setDimensionId(VarInts.readInt(buffer));
        packet.setSpawnPosition(helper.readBlockPosition(buffer));
    }
}
