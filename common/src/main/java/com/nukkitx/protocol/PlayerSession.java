package com.nukkitx.protocol;

import com.nukkitx.network.util.DisconnectReason;

import javax.annotation.Nonnull;

public interface PlayerSession {

    boolean isClosed();

    void close();

    void onDisconnect(@Nonnull DisconnectReason reason);

    void onDisconnect(@Nonnull String reason);
}
