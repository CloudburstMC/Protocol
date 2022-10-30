package org.cloudburstmc.protocol.common;

import javax.annotation.Nonnull;

public interface PlayerSession {

    boolean isClosed();

    void close();

    void onDisconnect(@Nonnull String reason);
}
