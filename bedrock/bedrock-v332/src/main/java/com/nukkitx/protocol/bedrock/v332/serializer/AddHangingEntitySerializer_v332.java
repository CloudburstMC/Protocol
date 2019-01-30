package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddHangingEntityPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddHangingEntitySerializer_v332 implements PacketSerializer<AddHangingEntityPacket> {
    public static final AddHangingEntitySerializer_v332 INSTANCE = new AddHangingEntitySerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, AddHangingEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeVector3i(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getRotation());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddHangingEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setRotation(readInt(buffer));
    }
}
