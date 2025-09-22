package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v554.serializer.TextSerializer_v554;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;

public class TextSerializer_v685 extends TextSerializer_v554 {
    public static final TextSerializer_v685 INSTANCE = new TextSerializer_v685();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeString(buffer, packet.getFilteredMessage());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setFilteredMessage(helper.readString(buffer));
    }
}