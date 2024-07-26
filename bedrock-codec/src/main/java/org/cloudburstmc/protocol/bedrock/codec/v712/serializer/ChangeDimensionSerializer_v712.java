package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ChangeDimensionSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.ChangeDimensionPacket;

public class ChangeDimensionSerializer_v712 extends ChangeDimensionSerializer_v291 {
    public static final ChangeDimensionSerializer_v712 INSTANCE = new ChangeDimensionSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ChangeDimensionPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getLoadingScreenId(), ByteBuf::writeIntLE);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ChangeDimensionPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setLoadingScreenId(helper.readOptional(buffer, null, ByteBuf::readIntLE));
    }
}