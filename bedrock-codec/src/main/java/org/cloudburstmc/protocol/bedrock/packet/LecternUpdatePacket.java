package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LecternUpdatePacket implements BedrockPacket {
    private int page;
    private int totalPages;
    private Vector3i blockPosition;
    /**
     * @deprecated since v662
     */
    @Deprecated
    private boolean droppingBook;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LECTERN_UPDATE;
    }
}
