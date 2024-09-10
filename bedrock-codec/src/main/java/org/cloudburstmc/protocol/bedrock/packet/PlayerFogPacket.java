package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Tracks the current fog effects applied to a client
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerFogPacket implements BedrockPacket {

    /**
     * Fog stack containing fog effects from the /fog command
     *
     * @param fogStack list of fog effects
     * @return list of fog effects
     */
    private final List<String> fogStack = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_FOG;
    }

    @Override
    public PlayerFogPacket clone() {
        try {
            return (PlayerFogPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

