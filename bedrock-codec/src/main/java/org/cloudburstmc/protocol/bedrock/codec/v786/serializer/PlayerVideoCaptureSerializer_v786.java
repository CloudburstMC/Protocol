package org.cloudburstmc.protocol.bedrock.codec.v786.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayerVideoCapturePacket;

public class PlayerVideoCaptureSerializer_v786 implements BedrockPacketSerializer<PlayerVideoCapturePacket> {
    public static final PlayerVideoCaptureSerializer_v786 INSTANCE = new PlayerVideoCaptureSerializer_v786();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerVideoCapturePacket packet) {
        if (packet.getCaptureAction().equals(PlayerVideoCapturePacket.Action.START_VIDEO_CAPTURE)) {
            buffer.writeBoolean(packet.isAction());
            buffer.writeIntLE(packet.getFrameRate());
            helper.writeString(buffer, packet.getFilePrefix());
        } else if (packet.getCaptureAction().equals(PlayerVideoCapturePacket.Action.STOP_VIDEO_CAPTURE)) {
            buffer.writeBoolean(packet.isAction());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerVideoCapturePacket packet) {
        if (buffer.readableBytes() > 1) {
            packet.setAction(buffer.readBoolean());
            packet.setFrameRate(buffer.readIntLE());
            packet.setFilePrefix(helper.readString(buffer));
        } else if (buffer.readableBytes() == 1) {
            packet.setAction(buffer.readBoolean());
        } else {
            packet.setCaptureAction(PlayerVideoCapturePacket.Action.DEFAULT);
        }
    }
}