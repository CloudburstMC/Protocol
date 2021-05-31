package org.cloudburstmc.protocol.common;

import com.nukkitx.network.util.DisconnectReason;

import javax.annotation.Nonnull;

public interface PlayerSession {

    boolean isClosed();

    void close();

    void onDisconnect(@Nonnull DisconnectReason reason);

    void onDisconnect(@Nonnull String reason);
}
