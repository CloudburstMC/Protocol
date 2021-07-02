package org.cloudburstmc.protocol.bedrock.codec.v448.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.SetTitleSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.SetTitlePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTitleSerializer_v448 extends SetTitleSerializer_v291 {

    public static final SetTitleSerializer_v448 INSTANCE = new SetTitleSerializer_v448();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetTitlePacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeString(buffer, packet.getXuid());
        helper.writeString(buffer, packet.getPlatformOnlineId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetTitlePacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setXuid(helper.readString(buffer));
        packet.setPlatformOnlineId(helper.readString(buffer));
    }
}
