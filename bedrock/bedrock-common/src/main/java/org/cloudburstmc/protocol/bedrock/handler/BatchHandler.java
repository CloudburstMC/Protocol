package org.cloudburstmc.protocol.bedrock.handler;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.BedrockSession;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

import java.util.Collection;

public interface BatchHandler {

    void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets);
}
