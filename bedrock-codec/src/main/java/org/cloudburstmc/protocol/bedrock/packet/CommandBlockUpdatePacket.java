package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.CommandBlockMode;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CommandBlockUpdatePacket implements BedrockPacket {
    private boolean block;
    private Vector3i blockPosition;
    private CommandBlockMode mode;
    private boolean redstoneMode;
    private boolean conditional;
    private long minecartRuntimeEntityId;
    private String command;
    private String lastOutput;
    private String name;
    private boolean outputTracked;
    private long tickDelay;
    private boolean executingOnFirstTick;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.COMMAND_BLOCK_UPDATE;
    }

    @Override
    public CommandBlockUpdatePacket clone() {
        try {
            return (CommandBlockUpdatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

