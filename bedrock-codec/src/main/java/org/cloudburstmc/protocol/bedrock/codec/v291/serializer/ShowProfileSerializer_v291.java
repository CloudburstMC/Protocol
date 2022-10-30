package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ShowProfilePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowProfileSerializer_v291 implements BedrockPacketSerializer<ShowProfilePacket> {
    public static final ShowProfileSerializer_v291 INSTANCE = new ShowProfileSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ShowProfilePacket packet) {
        helper.writeString(buffer, packet.getXuid());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ShowProfilePacket packet) {
        packet.setXuid(helper.readString(buffer));
    }
}
