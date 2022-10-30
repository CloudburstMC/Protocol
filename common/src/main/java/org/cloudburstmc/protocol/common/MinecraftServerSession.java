package org.cloudburstmc.protocol.common;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface MinecraftServerSession<T extends MinecraftPacket> extends MinecraftSession<T> {

    void disconnect(@Nullable String reason);
}
