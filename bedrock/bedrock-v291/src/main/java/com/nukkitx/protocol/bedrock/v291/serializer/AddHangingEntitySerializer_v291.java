package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AddHangingEntityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddHangingEntitySerializer_v291 implements BedrockPacketSerializer<AddHangingEntityPacket> {
    public static final AddHangingEntitySerializer_v291 INSTANCE = new AddHangingEntitySerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddHangingEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeBlockPosition(buffer, packet.getPosition().toInt());
        VarInts.writeInt(buffer, packet.getDirection());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddHangingEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(helper.readBlockPosition(buffer).toFloat());
        packet.setDirection(readInt(buffer));
    }
}
