package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    public enum Action {
        OPEN,
        CLOSE
    }
}
