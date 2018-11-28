package com.nukkitx.protocol.bedrock.session.data;

import java.util.UUID;

public interface AuthData {
    String getDisplayName();

    UUID getIdentity();

    String getXuid();
}
