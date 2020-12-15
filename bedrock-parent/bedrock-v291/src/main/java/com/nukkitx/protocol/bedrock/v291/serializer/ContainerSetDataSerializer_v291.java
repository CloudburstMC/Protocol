package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerSetDataSerializer_v291 implements BedrockPacketSerializer<ContainerSetDataPacket> {
    public static final ContainerSetDataSerializer_v291 INSTANCE = new ContainerSetDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerSetDataPacket packet) {
        buffer.writeByte(packet.getWindowId());
        VarInts.writeInt(buffer, packet.getProperty());
        VarInts.writeInt(buffer, packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerSetDataPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setProperty(VarInts.readInt(buffer));
        packet.setValue(VarInts.readInt(buffer));
    }
}
