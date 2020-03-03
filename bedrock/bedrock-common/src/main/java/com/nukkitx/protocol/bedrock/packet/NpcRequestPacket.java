package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class NpcRequestPacket extends BedrockPacket {
    private long runtimeEntityId;
    private Type requestType;
    private String command;
    private int actionType;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NPC_REQUEST;
    }

    public enum Type {
        SET_ACTION,
        EXECUTE_COMMAND_ACTION,
        EXECUTE_CLOSING_COMMANDS
    }
}
