package org.cloudburstmc.protocol.bedrock.codec.v582.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.TrimMaterial;
import org.cloudburstmc.protocol.bedrock.data.TrimPattern;
import org.cloudburstmc.protocol.bedrock.packet.OpenSignPacket;
import org.cloudburstmc.protocol.bedrock.packet.TrimDataPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenSignSerializer_v582 implements BedrockPacketSerializer<OpenSignPacket> {
    public static final OpenSignSerializer_v582 INSTANCE = new OpenSignSerializer_v582();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, OpenSignPacket packet) {
        helper.writeBlockPosition(buffer, packet.getPosition());
        buffer.writeBoolean(packet.isFrontSide());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, OpenSignPacket packet) {
        packet.setPosition(helper.readBlockPosition(buffer));
        packet.setFrontSide(buffer.readBoolean());
    }
}
