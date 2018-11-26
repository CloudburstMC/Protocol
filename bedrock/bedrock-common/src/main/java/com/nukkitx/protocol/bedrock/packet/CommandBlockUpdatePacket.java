package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CommandBlockUpdatePacket extends BedrockPacket {
    protected boolean block;
    protected Vector3i blockPosition;
    protected int commandBlockMode;
    protected boolean redstoneMode;
    protected boolean conditional;
    protected long minecartRuntimeEntityId;
    protected String command;
    protected String lastOutput;
    protected String name;
    protected boolean outputTracked;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
