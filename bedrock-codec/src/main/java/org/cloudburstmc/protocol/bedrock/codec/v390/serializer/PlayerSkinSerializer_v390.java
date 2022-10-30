package org.cloudburstmc.protocol.bedrock.codec.v390.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.PlayerSkinSerializer_v388;
import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerSkinSerializer_v390 extends PlayerSkinSerializer_v388 {
    public static final PlayerSkinSerializer_v390 INSTANCE = new PlayerSkinSerializer_v390();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerSkinPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeBoolean(packet.isTrustedSkin());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerSkinPacket packet) {
        super.deserialize(buffer, helper, packet);

        if (buffer.isReadable()) packet.setTrustedSkin(buffer.readBoolean());
    }
}
