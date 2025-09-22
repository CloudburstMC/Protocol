package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisconnectSerializer_v291 implements BedrockPacketSerializer<DisconnectPacket> {
    public static final DisconnectSerializer_v291 INSTANCE = new DisconnectSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DisconnectPacket packet) {
        buffer.writeBoolean(packet.isMessageSkipped());
        if (!packet.isMessageSkipped()) {
            helper.writeString(buffer, packet.getKickMessage());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DisconnectPacket packet) {
        packet.setMessageSkipped(buffer.readBoolean());
        if (!packet.isMessageSkipped()) {
            packet.setKickMessage(helper.readString(buffer));
        }
    }
}
