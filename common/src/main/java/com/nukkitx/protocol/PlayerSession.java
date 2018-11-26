package com.nukkitx.protocol;

import javax.annotation.Nullable;

public interface PlayerSession {

    void close();

    default void onDisconnect() {
        onDisconnect(null);
    }

    void onDisconnect(@Nullable String reason);

    void onTimeout();
}
