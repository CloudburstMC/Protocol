package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisconnectSerializer_v291 implements BedrockPacketSerializer<DisconnectPacket> {
    public static final DisconnectSerializer_v291 INSTANCE = new DisconnectSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DisconnectPacket packet) {
        buffer.writeBoolean(packet.isMessageSkipped());
        if (!packet.isMessageSkipped()) {
            helper.writeString(buffer, packet.getKickMessage());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DisconnectPacket packet) {
        packet.setMessageSkipped(buffer.readBoolean());
        if (!packet.isMessageSkipped()) {
            packet.setKickMessage(helper.readString(buffer));
        }
    }
}
