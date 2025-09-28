package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.SetTitleSerializer_v448;
import org.cloudburstmc.protocol.bedrock.packet.SetTitlePacket;
import org.cloudburstmc.protocol.common.util.TextConverter;

public class SetTitleSerializer_v712 extends SetTitleSerializer_v448 {
    public static final SetTitleSerializer_v712 INSTANCE = new SetTitleSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetTitlePacket packet) {
        super.serialize(buffer, helper, packet);
        TextConverter converter = helper.getTextConverter();
        helper.writeString(buffer, converter.serialize(packet.getFilteredTitleText(CharSequence.class)));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetTitlePacket packet) {
        super.deserialize(buffer, helper, packet);
        TextConverter converter = helper.getTextConverter();
        packet.setFilteredTitleText(converter.deserialize(helper.readString(buffer)));
    }
}
