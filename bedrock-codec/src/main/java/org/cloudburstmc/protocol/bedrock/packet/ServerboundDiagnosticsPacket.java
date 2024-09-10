package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundDiagnosticsPacket implements BedrockPacket{
    private float avgFps;
    private float avgServerSimTickTimeMS;
    private float avgClientSimTickTimeMS;
    private float avgBeginFrameTimeMS;
    private float avgInputTimeMS;
    private float avgRenderTimeMS;
    private float avgEndFrameTimeMS;
    private float avgRemainderTimePercent;
    private float avgUnaccountedTimePercent;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_DIAGNOSTICS;
    }

    @Override
    public ServerboundDiagnosticsPacket clone() {
        try {
            return (ServerboundDiagnosticsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
