package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ContainerClosePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerCloseSerializer_v291 implements BedrockPacketSerializer<ContainerClosePacket> {
    public static final ContainerCloseSerializer_v291 INSTANCE = new ContainerCloseSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
        buffer.writeByte(packet.getId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
        packet.setId(buffer.readByte());
    }
}
