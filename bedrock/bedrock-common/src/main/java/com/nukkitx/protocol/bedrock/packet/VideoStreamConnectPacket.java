package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VideoStreamConnectPacket extends BedrockPacket {
    private String address;
    private float screenshotFrequency;
    private Action action;
    private int width;
    private int height;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.VIDEO_STREAM_CONNECT;
    }

    public enum Action {
        OPEN,
        CLOSE
    }
}
