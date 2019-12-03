package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerSetDataSerializer_v291 implements PacketSerializer<ContainerSetDataPacket> {
    public static final ContainerSetDataSerializer_v291 INSTANCE = new ContainerSetDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, ContainerSetDataPacket packet) {
        buffer.writeByte(packet.getWindowId());
        VarInts.writeInt(buffer, packet.getProperty());
        VarInts.writeInt(buffer, packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, ContainerSetDataPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setProperty(VarInts.readInt(buffer));
        packet.setValue(VarInts.readInt(buffer));
    }
}
