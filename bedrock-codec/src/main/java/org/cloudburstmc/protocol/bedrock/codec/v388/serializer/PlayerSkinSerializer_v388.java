package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerSkinSerializer_v388 implements BedrockPacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v388 INSTANCE = new PlayerSkinSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerSkinPacket packet) {
        helper.writeUuid(buffer, packet.getUuid());
        helper.writeSkin(buffer, packet.getSkin());
        helper.writeString(buffer, packet.getNewSkinName());
        helper.writeString(buffer, packet.getOldSkinName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerSkinPacket packet) {
        packet.setUuid(helper.readUuid(buffer));
        packet.setSkin(helper.readSkin(buffer));
        packet.setNewSkinName(helper.readString(buffer));
        packet.setOldSkinName(helper.readString(buffer));
    }
}
