package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddHangingEntityPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddHangingEntitySerializer_v313 implements PacketSerializer<AddHangingEntityPacket> {
    public static final AddHangingEntitySerializer_v313 INSTANCE = new AddHangingEntitySerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, AddHangingEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeBlockPosition(buffer, packet.getPosition().toInt());
        VarInts.writeInt(buffer, packet.getDirection());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddHangingEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(BedrockUtils.readBlockPosition(buffer).toFloat());
        packet.setDirection(readInt(buffer));
    }
}
