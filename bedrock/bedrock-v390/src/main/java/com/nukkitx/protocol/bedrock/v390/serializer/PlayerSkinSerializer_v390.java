package com.nukkitx.protocol.bedrock.v390.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.v388.serializer.PlayerSkinSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerSkinSerializer_v390 extends PlayerSkinSerializer_v388 {
    public static final PlayerSkinSerializer_v390 INSTANCE = new PlayerSkinSerializer_v390();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeBoolean(packet.isTrustedSkin());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
        super.deserialize(buffer, helper, packet);

        if (buffer.isReadable()) packet.setTrustedSkin(buffer.readBoolean());
    }
}
