package org.cloudburstmc.protocol.common;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface PlayerSession {

    boolean isClosed();

    void close();

    void onDisconnect(@NonNull String reason);
}
