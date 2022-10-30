package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ContainerClosePacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerCloseSerializer_v419 implements BedrockPacketSerializer<ContainerClosePacket> {

    public static final ContainerCloseSerializer_v419 INSTANCE = new ContainerCloseSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerClosePacket packet) {
        buffer.writeByte(packet.getId());
        buffer.writeBoolean(packet.isServerInitiated());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerClosePacket packet) {
        packet.setId(buffer.readByte());
        packet.setServerInitiated(buffer.readBoolean());
    }
}
