package com.nukkitx.protocol.bedrock.handler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultBatchHandler implements BatchHandler {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(DefaultBatchHandler.class);
    public static final DefaultBatchHandler INSTANCE = new DefaultBatchHandler();

    @Override
    public void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets) {
        for (BedrockPacket packet : packets) {
            if (session.isLogging() && log.isTraceEnabled()) {
                log.trace("Inbound {}: {}", session.getAddress(), packet);
            }

            BedrockPacketHandler handler = session.getPacketHandler();
            if (handler == null || !packet.handle(handler)) {
                log.debug("Unhandled packet for {}: {}", session.getAddress(), packet);
            }
        }
    }
}
