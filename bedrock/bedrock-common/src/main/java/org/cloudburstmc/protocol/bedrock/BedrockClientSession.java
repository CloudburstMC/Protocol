package org.cloudburstmc.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetSession;
import io.netty.channel.EventLoop;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializer;

public class BedrockClientSession extends BedrockSession {

    BedrockClientSession(RakNetSession connection, EventLoop eventLoop, BedrockWrapperSerializer serializer) {
        super(connection, eventLoop, serializer);
    }

    @Override
    public void disconnect() {
        this.checkForClosed();
        this.connection.disconnect();
    }
}
