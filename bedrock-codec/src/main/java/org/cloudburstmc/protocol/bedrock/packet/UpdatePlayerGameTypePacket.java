package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
public class UpdatePlayerGameTypePacket implements BedrockPacket {
    private GameType gameType;
    private long entityId;
    /**
     * @since v671
     */
    private long tick;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_PLAYER_GAME_TYPE;
    }

    @Override
    public UpdatePlayerGameTypePacket clone() {
        try {
            return (UpdatePlayerGameTypePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

