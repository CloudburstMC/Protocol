package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.util.AsciiString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false, exclude = {"skinData"})
@ToString(doNotUseGetters = true)
@ToString(exclude = {"chainData", "skinData"})
public class LoginPacket implements BedrockPacket {
    private int protocolVersion;
    private AsciiString chainData;
    private AsciiString skinData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LOGIN;
    }
}
