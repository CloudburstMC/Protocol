package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ToastRequestPacket;
import org.cloudburstmc.protocol.common.util.TextConverter;

@NoArgsConstructor
public class ToastRequestSerializer_v527 implements BedrockPacketSerializer<ToastRequestPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ToastRequestPacket packet) {
        TextConverter converter = helper.getTextConverter();
        helper.writeString(buffer, converter.serialize(packet.getTitle(CharSequence.class)));
        helper.writeString(buffer, converter.serialize(packet.getContent(CharSequence.class)));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ToastRequestPacket packet) {
        TextConverter converter = helper.getTextConverter();
        packet.setTitle(converter.deserialize(helper.readString(buffer)));
        packet.setContent(converter.deserialize(helper.readString(buffer)));
    }
}
