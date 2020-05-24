package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPaintingSerializer_v363 implements PacketSerializer<AddPaintingPacket> {
    public static final AddPaintingSerializer_v363 INSTANCE = new AddPaintingSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, AddPaintingPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getDirection());
        BedrockUtils.writeString(buffer, packet.getName());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddPaintingPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setDirection(readInt(buffer));
        packet.setName(BedrockUtils.readString(buffer));
    }
}
