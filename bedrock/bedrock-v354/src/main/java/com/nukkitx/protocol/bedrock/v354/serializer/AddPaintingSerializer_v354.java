package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPaintingSerializer_v354 implements PacketSerializer<AddPaintingPacket> {
    public static final AddPaintingSerializer_v354 INSTANCE = new AddPaintingSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, AddPaintingPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeBlockPosition(buffer, packet.getPosition().toInt());
        VarInts.writeInt(buffer, packet.getDirection());
        BedrockUtils.writeString(buffer, packet.getName());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddPaintingPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(BedrockUtils.readBlockPosition(buffer).toFloat());
        packet.setDirection(readInt(buffer));
        packet.setName(BedrockUtils.readString(buffer));
    }
}
