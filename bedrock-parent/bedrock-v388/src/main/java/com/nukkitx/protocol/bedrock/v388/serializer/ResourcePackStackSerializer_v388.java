package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.v313.serializer.ResourcePackStackSerializer_v313;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackStackSerializer_v388 extends ResourcePackStackSerializer_v313 {
    public static final ResourcePackStackSerializer_v388 INSTANCE = new ResourcePackStackSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeString(buffer, packet.getGameVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setGameVersion(helper.readString(buffer));
    }
}
