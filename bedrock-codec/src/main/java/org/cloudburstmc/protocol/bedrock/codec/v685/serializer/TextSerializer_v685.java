package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v554.serializer.TextSerializer_v554;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.cloudburstmc.protocol.common.util.TextConverter;

public class TextSerializer_v685 extends TextSerializer_v554 {
    public static final TextSerializer_v685 INSTANCE = new TextSerializer_v685();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        super.serialize(buffer, helper, packet);
        TextConverter converter = helper.getTextConverter();
        helper.writeString(buffer, converter.serialize(packet.getFilteredMessage(CharSequence.class)));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        int readerIndex = buffer.readerIndex();
        super.deserialize(buffer, helper, packet);
        boolean needsTranslation = buffer.getBoolean(readerIndex + 1);
        TextConverter converter = helper.getTextConverter();
        packet.setFilteredMessage(converter.deserialize(helper.readString(buffer), needsTranslation));
    }
}
