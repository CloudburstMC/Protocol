package com.nukkitx.protocol.bedrock.session.data;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthData {
    private String displayName;
    private UUID identity;
    private String xuid;
}
