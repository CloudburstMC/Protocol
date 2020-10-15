package com.nukkitx.protocol.bedrock.v419.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ContainerClosePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerCloseSerializer_v419 implements BedrockPacketSerializer<ContainerClosePacket> {

    public static final ContainerCloseSerializer_v419 INSTANCE = new ContainerCloseSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
        buffer.writeByte(packet.getId());
        buffer.writeBoolean(packet.isUnknownBool0());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
        packet.setId(buffer.readByte());
        packet.setUnknownBool0(buffer.readBoolean());
    }
}
