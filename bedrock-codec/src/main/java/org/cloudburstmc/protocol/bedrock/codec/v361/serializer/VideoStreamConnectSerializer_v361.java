package org.cloudburstmc.protocol.bedrock.codec.v361.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.VideoStreamConnectPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoStreamConnectSerializer_v361 implements BedrockPacketSerializer<VideoStreamConnectPacket> {
    public static final VideoStreamConnectSerializer_v361 INSTANCE = new VideoStreamConnectSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, VideoStreamConnectPacket packet) {
        helper.writeString(buffer, packet.getAddress());
        buffer.writeFloatLE(packet.getScreenshotFrequency());
        buffer.writeByte(packet.getAction().ordinal());
        buffer.writeIntLE(packet.getWidth());
        buffer.writeIntLE(packet.getHeight());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, VideoStreamConnectPacket packet) {
        packet.setAddress(helper.readString(buffer));
        packet.setScreenshotFrequency(buffer.readFloatLE());
        packet.setAction(VideoStreamConnectPacket.Action.values()[buffer.readUnsignedByte()]);
        packet.setWidth(buffer.readIntLE());
        packet.setHeight(buffer.readIntLE());
    }
}
