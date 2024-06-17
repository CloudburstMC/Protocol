package org.cloudburstmc.protocol.bedrock.codec.v705.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.SetTitleSerializer_v448;
import org.cloudburstmc.protocol.bedrock.packet.SetTitlePacket;

public class SetTitleSerializer_v705 extends SetTitleSerializer_v448 {
    public static final SetTitleSerializer_v705 INSTANCE = new SetTitleSerializer_v705();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetTitlePacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeString(buffer, packet.getFilteredTitleText());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetTitlePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setFilteredTitleText(helper.readString(buffer));
    }
}