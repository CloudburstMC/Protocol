package com.nukkitx.protocol.bedrock.v527.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ToastRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToastRequestSerializer_v527 implements BedrockPacketSerializer<ToastRequestPacket> {
    public static final ToastRequestSerializer_v527 INSTANCE = new ToastRequestSerializer_v527();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ToastRequestPacket packet) {
        helper.writeString(buffer, packet.getTitle());
        helper.writeString(buffer, packet.getContent());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ToastRequestPacket packet) {
        packet.setTitle(helper.readString(buffer));
        packet.setContent(helper.readString(buffer));
    }
}
