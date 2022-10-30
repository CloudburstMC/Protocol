package org.cloudburstmc.protocol.bedrock.codec.compat.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;

public class PlayStatusSerializerCompat implements BedrockPacketSerializer<PlayStatusPacket> {
    public static final PlayStatusSerializerCompat INSTANCE = new PlayStatusSerializerCompat();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayStatusPacket packet) {
        buffer.writeInt(packet.getStatus().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayStatusPacket packet) {
        packet.setStatus(PlayStatusPacket.Status.values()[buffer.readInt()]);
    }
}

