package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerVideoCapturePacket implements BedrockPacket {
    private Action captureAction;
    private boolean action;
    private int frameRate;
    private String filePrefix;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_VIDEO_CAPTURE;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PlayerVideoCapturePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum Action {
        START_VIDEO_CAPTURE,
        STOP_VIDEO_CAPTURE,
        DEFAULT
    }
}