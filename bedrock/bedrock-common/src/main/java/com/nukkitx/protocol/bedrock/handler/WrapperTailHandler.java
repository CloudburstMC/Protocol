package com.nukkitx.protocol.bedrock.handler;

import com.nukkitx.protocol.PlayerSession;
import com.nukkitx.protocol.bedrock.wrapper.WrappedPacket;

public interface WrapperTailHandler<PLAYER extends PlayerSession> {

    void handle(WrappedPacket<PLAYER> packet, boolean packetsHandled);
}
