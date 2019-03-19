package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPaintingSerializer_v340 implements PacketSerializer<AddPaintingPacket> {
    public static final AddPaintingSerializer_v340 INSTANCE = new AddPaintingSerializer_v340();

    @Override
    public void serialize(ByteBuf buffer, AddPaintingPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeVector3i(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getRotation());
        BedrockUtils.writeString(buffer, packet.getTitle());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddPaintingPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setRotation(readInt(buffer));
        packet.setTitle(BedrockUtils.readString(buffer));
    }
}
