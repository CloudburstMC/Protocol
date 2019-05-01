package com.nukkitx.protocol.bedrock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;

@ParametersAreNonnullByDefault
public interface BedrockServerEventHandler {

    boolean onConnectionRequest(InetSocketAddress address);

    @Nullable
    BedrockPong onQuery(InetSocketAddress address);

    void onSessionCreation(BedrockServerSession serverSession);
}