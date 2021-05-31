package org.cloudburstmc.protocol.common;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public interface MinecraftInterface {

    CompletableFuture<Void> bind();

    void close();

    InetSocketAddress getBindAddress();
}
