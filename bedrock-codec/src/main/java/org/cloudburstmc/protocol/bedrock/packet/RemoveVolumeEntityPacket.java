package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class RemoveVolumeEntityPacket implements BedrockPacket {
    private int id;
    /**
     * @since v503
     */
    private int dimension;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REMOVE_VOLUME_ENTITY;
    }

    @Override
    public RemoveVolumeEntityPacket clone() {
        try {
            return (RemoveVolumeEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

