package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.packet.VideoStreamConnectPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoStreamConnectSerializer_v354 implements PacketSerializer<VideoStreamConnectPacket> {
    public static final VideoStreamConnectSerializer_v354 INSTANCE = new VideoStreamConnectSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, VideoStreamConnectPacket packet) {
        BedrockUtils.writeString(buffer, packet.getAddress());
        buffer.writeFloatLE(packet.getScreenshotFrequency());
        buffer.writeByte(packet.getAction().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, VideoStreamConnectPacket packet) {
        packet.setAddress(BedrockUtils.readString(buffer));
        packet.setScreenshotFrequency(buffer.readFloatLE());
        packet.setAction(VideoStreamConnectPacket.Action.values()[buffer.readUnsignedByte()]);
    }
}
