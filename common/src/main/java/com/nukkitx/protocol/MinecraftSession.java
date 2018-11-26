package com.nukkitx.protocol;

import com.nukkitx.network.NetworkSession;
import com.nukkitx.network.SessionConnection;

import javax.annotation.Nullable;

public interface MinecraftSession<PLAYER extends PlayerSession, C extends SessionConnection<?>> extends NetworkSession<C> {

    boolean isClosed();

    default void disconnect() {
        disconnect(null);
    }

    void disconnect(@Nullable String reason);

    PLAYER getPlayer();
}
