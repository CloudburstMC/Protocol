package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class VideoStreamConnectPacket implements BedrockPacket {
    private String address;
    private float screenshotFrequency;
    private Action action;
    private int width;
    private int height;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.VIDEO_STREAM_CONNECT;
    }

    public enum Action {
        OPEN,
        CLOSE
    }

    @Override
    public VideoStreamConnectPacket clone() {
        try {
            return (VideoStreamConnectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

