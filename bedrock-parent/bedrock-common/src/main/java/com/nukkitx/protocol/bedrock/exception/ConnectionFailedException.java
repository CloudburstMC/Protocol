package com.nukkitx.protocol.bedrock.exception;

import com.nukkitx.network.util.DisconnectReason;

public class ConnectionFailedException extends Exception {
    private final DisconnectReason reason;

    public ConnectionFailedException(DisconnectReason reason) {
        super("Failed to connect to remote peer: " + reason.name());
        this.reason = reason;
    }

    public DisconnectReason getReason() {
        return reason;
    }
}
