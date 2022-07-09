package com.nukkitx.protocol.bedrock.v534.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.DeathInfoPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeathInfoSerializer_v534 implements BedrockPacketSerializer<DeathInfoPacket> {
    public static final DeathInfoSerializer_v534 INSTANCE = new DeathInfoSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DeathInfoPacket packet) {
        helper.writeString(buffer, packet.getCauseAttackName());
        helper.writeArray(buffer, packet.getMessageList(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DeathInfoPacket packet) {
        packet.setCauseAttackName(helper.readString(buffer));
        helper.readArray(buffer, packet.getMessageList(), helper::readString);
    }
}
