package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;

/*
WTF were they thinking?
 */
public class LevelSoundEvent3Packet extends LevelSoundEvent2Packet {

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
