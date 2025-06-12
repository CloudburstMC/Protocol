package org.cloudburstmc.protocol.bedrock.data.auth;

import lombok.Getter;
import org.cloudburstmc.protocol.common.util.Preconditions;

import java.util.Objects;

public class TokenPayload implements AuthPayload {

    @Getter
    private final String token;
    private final AuthType type;

    public TokenPayload(String token, AuthType type) {
        Preconditions.checkArgument(type != AuthType.UNKNOWN, "TokenPayload cannot be of type UNKNOWN");
        this.token = token;
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public AuthType getAuthType() {
        return type;
    }
}
