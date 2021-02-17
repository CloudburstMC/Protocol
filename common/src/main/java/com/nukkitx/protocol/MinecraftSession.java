package com.nukkitx.protocol;

import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;

@ParametersAreNonnullByDefault
public interface MinecraftSession<T extends MinecraftPacket> {

    boolean isClosed();

    void disconnect();

    InetSocketAddress getAddress();

    default InetSocketAddress getRealAddress() {
        return getAddress();
    }

    void sendPacket(T packet);

    void sendPacketImmediately(T packet);

    long getLatency();
}
