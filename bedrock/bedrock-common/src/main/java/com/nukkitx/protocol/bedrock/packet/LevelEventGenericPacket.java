package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LevelEventGenericPacket extends BedrockPacket {
    private int eventId;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
