package org.cloudburstmc.protocol.bedrock.util;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class TokenValidationResult implements ValidationResult {
    private final boolean signed;
    private final JwtContext jwtContext;

    private IdentityClaims identityClaims;

    public TokenValidationResult(boolean signed, JwtContext context) {
        this.signed = signed;
        this.jwtContext = Objects.requireNonNull(context);
    }

    public boolean signed() {
        return signed;
    }

    public Map<String, Object> rawIdentityClaims() {
        return jwtContext.getJwtClaims().getClaimsMap();
    }

    public IdentityClaims identityClaims() {
        if (identityClaims == null) {
            identityClaims = createClaims();
        }
        return identityClaims;
    }

    private IdentityClaims createClaims() {
        JwtClaims claims = jwtContext.getJwtClaims();

        String identityPublicKey = claims.getClaimValueAsString("cpk");
        String displayName = claims.getClaimValueAsString("xname");
        String xuid = claims.getClaimValueAsString("xid");
        String minecraftId = claims.getClaimValueAsString("mid");
        UUID identity = UUID.nameUUIDFromBytes(("pocket-auth-1-xuid:" + xuid).getBytes(StandardCharsets.UTF_8));

        return new IdentityClaims(
                new IdentityData(displayName, identity, xuid, null, minecraftId),
                identityPublicKey
        );
    }
}
