package org.cloudburstmc.protocol.java;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface JavaServerEventHandler {
    void onSessionCreation(JavaServerSession session);
}