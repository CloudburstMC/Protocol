package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerOpenSerializer_v363 implements PacketSerializer<ContainerOpenPacket> {
    public static final ContainerOpenSerializer_v363 INSTANCE = new ContainerOpenSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, ContainerOpenPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getType());
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, ContainerOpenPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setType(buffer.readByte());
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
    }
}
