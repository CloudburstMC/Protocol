package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetEntityDataPacket implements BedrockPacket {
    private EntityDataMap metadata = new EntityDataMap();
    private long runtimeEntityId;
    private long tick;
    /**
     * @since v557
     */
    private EntityProperties properties = new EntityProperties();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_ENTITY_DATA;
    }

    @Override
    public SetEntityDataPacket clone() {
        try {
            return (SetEntityDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

