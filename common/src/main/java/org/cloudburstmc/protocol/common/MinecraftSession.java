package org.cloudburstmc.protocol.common;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.net.InetSocketAddress;

public interface MinecraftSession<T extends MinecraftPacket> {

    boolean isClosed();

    void disconnect();

    InetSocketAddress getAddress();

    default InetSocketAddress getRealAddress() {
        return getAddress();
    }

    void sendPacket(@NonNull T packet);

    void sendPacketImmediately(@NonNull T packet);

    long getLatency();
}
