package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NpcRequestPacket extends BedrockPacket {
    protected long runtimeEntityId;
    protected Type requestType;
    protected String command;
    protected int actionType;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        SET_ACTION,
        EXECUTE_COMMAND_ACTION,
        EXECUTE_CLOSING_COMMANDS
    }
}
