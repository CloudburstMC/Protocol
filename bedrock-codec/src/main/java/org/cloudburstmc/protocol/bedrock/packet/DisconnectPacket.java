package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DisconnectPacket implements BedrockPacket {
    private DisconnectFailReason reason = DisconnectFailReason.UNKNOWN;
    private boolean messageSkipped;
    private Component kickMessage;
    /**
     * @since v712
     */
    private Component filteredMessage = Component.empty();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DISCONNECT;
    }

    @Override
    public DisconnectPacket clone() {
        try {
            return (DisconnectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

