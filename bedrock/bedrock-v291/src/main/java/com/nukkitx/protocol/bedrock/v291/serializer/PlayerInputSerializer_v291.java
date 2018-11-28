package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.PlayerInputPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerInputSerializer_v291 implements PacketSerializer<PlayerInputPacket> {
    public static final PlayerInputSerializer_v291 INSTANCE = new PlayerInputSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, PlayerInputPacket packet) {
        BedrockUtils.writeVector2f(buffer, packet.getInputMotion());
        buffer.writeBoolean(packet.isJumping());
        buffer.writeBoolean(packet.isSneaking());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerInputPacket packet) {
        packet.setInputMotion(BedrockUtils.readVector2f(buffer));
        packet.setJumping(buffer.readBoolean());
        packet.setSneaking(buffer.readBoolean());
    }
}
