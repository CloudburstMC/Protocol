package org.cloudburstmc.protocol.common;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

public interface MinecraftInterface {

    Future<Void> bind(InetSocketAddress address);

    void close();

    InetSocketAddress getBindAddress();
}
