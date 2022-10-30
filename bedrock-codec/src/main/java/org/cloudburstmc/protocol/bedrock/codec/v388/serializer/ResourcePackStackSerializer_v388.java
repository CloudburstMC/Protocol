package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.ResourcePackStackSerializer_v313;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackStackSerializer_v388 extends ResourcePackStackSerializer_v313 {
    public static final ResourcePackStackSerializer_v388 INSTANCE = new ResourcePackStackSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackStackPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeString(buffer, packet.getGameVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackStackPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setGameVersion(helper.readString(buffer));
    }
}
