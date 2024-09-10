package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.NpcRequestType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NpcRequestPacket implements BedrockPacket {
    private long runtimeEntityId;
    private NpcRequestType requestType;
    private String command;
    private int actionType;
    /**
     * @since v448
     */
    private String sceneName;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NPC_REQUEST;
    }

    @Override
    public NpcRequestPacket clone() {
        try {
            return (NpcRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

