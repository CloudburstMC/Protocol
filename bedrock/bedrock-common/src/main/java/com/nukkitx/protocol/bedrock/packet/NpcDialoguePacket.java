package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class NpcDialoguePacket extends BedrockPacket {

    private long uniqueEntityId;
    private Action action;
    private String dialogue;
    private String sceneName;
    private String npcName;
    private String actionJson;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NPC_DIALOGUE;
    }

    public enum Action {
        OPEN,
        CLOSE
    }
}
