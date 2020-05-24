package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetSpawnPositionSerializer_v363 implements PacketSerializer<SetSpawnPositionPacket> {
    public static final SetSpawnPositionSerializer_v363 INSTANCE = new SetSpawnPositionSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, SetSpawnPositionPacket packet) {
        VarInts.writeInt(buffer, packet.getSpawnType().ordinal());
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        buffer.writeBoolean(packet.isSpawnForced());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetSpawnPositionPacket packet) {
        packet.setSpawnType(SetSpawnPositionPacket.Type.values()[VarInts.readInt(buffer)]);
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setSpawnForced(buffer.readBoolean());
    }
}
