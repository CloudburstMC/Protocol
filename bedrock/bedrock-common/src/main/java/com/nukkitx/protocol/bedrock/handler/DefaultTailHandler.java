package com.nukkitx.protocol.bedrock.handler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.session.BedrockSession;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultTailHandler implements TailHandler {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(DefaultTailHandler.class);

    private final BedrockSession session;

    @Override
    public void handle(BedrockPacket packet) {
        if (log.isDebugEnabled()) {
            log.debug("Unhandled packet for {} received: {}", session.getRemoteAddress().map(Object::toString).orElse("UNKNOWN"), packet);
        }
    }
}
