package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlayerInputPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerInputSerializer_v291 implements BedrockPacketSerializer<PlayerInputPacket> {
    public static final PlayerInputSerializer_v291 INSTANCE = new PlayerInputSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerInputPacket packet) {
        helper.writeVector2f(buffer, packet.getInputMotion());
        buffer.writeBoolean(packet.isJumping());
        buffer.writeBoolean(packet.isSneaking());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerInputPacket packet) {
        packet.setInputMotion(helper.readVector2f(buffer));
        packet.setJumping(buffer.readBoolean());
        packet.setSneaking(buffer.readBoolean());
    }
}
