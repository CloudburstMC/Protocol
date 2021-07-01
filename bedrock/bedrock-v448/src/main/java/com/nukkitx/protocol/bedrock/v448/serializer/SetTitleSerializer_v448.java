package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import com.nukkitx.protocol.bedrock.v291.serializer.SetTitleSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTitleSerializer_v448 extends SetTitleSerializer_v291 {

    public static final SetTitleSerializer_v448 INSTANCE = new SetTitleSerializer_v448();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeString(buffer, packet.getXuid());
        helper.writeString(buffer, packet.getPlatformOnlineId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setXuid(helper.readString(buffer));
        packet.setPlatformOnlineId(helper.readString(buffer));
    }
}
