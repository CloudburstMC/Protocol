package org.cloudburstmc.protocol.java.handler;

import org.cloudburstmc.protocol.java.packet.play.DisconnectPacket;

public interface JavaPlayPacketHandler extends JavaPacketHandler {

    default boolean handle(DisconnectPacket packet) {
        return false;
    }
}
