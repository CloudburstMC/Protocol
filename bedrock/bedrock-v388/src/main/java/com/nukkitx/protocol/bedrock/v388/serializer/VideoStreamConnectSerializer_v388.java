package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.VideoStreamConnectPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoStreamConnectSerializer_v388 implements PacketSerializer<VideoStreamConnectPacket> {
    public static final VideoStreamConnectSerializer_v388 INSTANCE = new VideoStreamConnectSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, VideoStreamConnectPacket packet) {
        BedrockUtils.writeString(buffer, packet.getAddress());
        buffer.writeFloatLE(packet.getScreenshotFrequency());
        buffer.writeByte(packet.getAction().ordinal());
        buffer.writeIntLE(packet.getWidth());
        buffer.writeIntLE(packet.getHeight());
    }

    @Override
    public void deserialize(ByteBuf buffer, VideoStreamConnectPacket packet) {
        packet.setAddress(BedrockUtils.readString(buffer));
        packet.setScreenshotFrequency(buffer.readFloatLE());
        packet.setAction(VideoStreamConnectPacket.Action.values()[buffer.readUnsignedByte()]);
        packet.setWidth(buffer.readIntLE());
        packet.setHeight(buffer.readIntLE());
    }
}
