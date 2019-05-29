package com.nukkitx.protocol;

import javax.annotation.Nullable;

public interface MinecraftServerSession<T extends MinecraftPacket> extends MinecraftSession<T> {

    void disconnect(@Nullable String reason);
}
