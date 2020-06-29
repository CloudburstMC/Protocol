package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.ResourcePackStackSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackStackSerializer_v313 extends ResourcePackStackSerializer_v291 {
    public static final ResourcePackStackSerializer_v313 INSTANCE = new ResourcePackStackSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeBoolean(packet.isExperimental());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setExperimental(buffer.readBoolean());
    }
}
