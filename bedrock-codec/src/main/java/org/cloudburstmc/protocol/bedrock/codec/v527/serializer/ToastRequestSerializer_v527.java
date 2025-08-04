package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ToastRequestPacket;

@NoArgsConstructor
public class ToastRequestSerializer_v527 implements BedrockPacketSerializer<ToastRequestPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ToastRequestPacket packet) {
        helper.writeComponent(buffer, packet.getTitle(), true);
        helper.writeComponent(buffer, packet.getContent(), true);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ToastRequestPacket packet) {
        packet.setTitle(helper.readComponent(buffer, false, true));
        packet.setContent(helper.readComponent(buffer, false, true));
    }
}
