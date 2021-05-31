package org.cloudburstmc.protocol.bedrock.handler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

import java.util.Collection;

public interface BatchHandler {

    void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets);
}
