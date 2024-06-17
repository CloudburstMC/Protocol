package org.cloudburstmc.protocol.bedrock.codec.v705.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ChangeDimensionSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.ChangeDimensionPacket;

import java.util.Optional;

public class ChangeDimensionSerializer_v705 extends ChangeDimensionSerializer_v291 {
    public static final ChangeDimensionSerializer_v705 INSTANCE = new ChangeDimensionSerializer_v705();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ChangeDimensionPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptional(buffer, Optional::isPresent, packet.getLoadingScreenId(), (byteBuf, loadingScreenId) -> byteBuf.writeIntLE(loadingScreenId.get()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ChangeDimensionPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setLoadingScreenId(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(byteBuf.readIntLE())));
    }
}