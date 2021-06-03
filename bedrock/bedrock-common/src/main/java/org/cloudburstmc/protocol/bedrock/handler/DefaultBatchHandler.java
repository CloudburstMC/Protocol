package org.cloudburstmc.protocol.bedrock.handler;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.BedrockSession;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;

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
            boolean release = true;
            try {
                if (handler != null && packet.handle(handler)) {
                    release = false;
                } else {
                    log.debug("Unhandled packet for {}: {}", session.getAddress(), packet);
                }
            } finally {
                if (release) {
                    ReferenceCountUtil.release(packet);
                }
            }
        }
    }
}
