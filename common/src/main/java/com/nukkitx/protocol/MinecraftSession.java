package com.nukkitx.protocol;

import javax.annotation.Nullable;

public interface MinecraftSession<PLAYER extends PlayerSession> {

    boolean isClosed();

    default void disconnect() {
        disconnect(null);
    }

    void disconnect(@Nullable String reason);

    PLAYER getPlayer();
}
