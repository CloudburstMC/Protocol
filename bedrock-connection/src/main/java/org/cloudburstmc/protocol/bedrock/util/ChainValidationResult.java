package org.cloudburstmc.protocol.bedrock.util;

import org.jose4j.json.JsonUtil;
import org.jose4j.lang.JoseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.cloudburstmc.protocol.bedrock.util.JsonUtils.childAsType;

public final class ChainValidationResult implements ValidationResult {
    private final boolean signed;
    private final Map<String, Object> parsedPayload;

    private IdentityClaims identityClaims;

    public ChainValidationResult(boolean signed, String rawPayload) throws JoseException {
        this(signed, JsonUtil.parseJson(rawPayload));
    }

    public ChainValidationResult(boolean signed, Map<String, Object> parsedPayload) {
        this.signed = signed;
        this.parsedPayload = Objects.requireNonNull(parsedPayload);
    }

    public boolean signed() {
        return signed;
    }

    public Map<String, Object> rawIdentityClaims() {
        return new HashMap<>(parsedPayload);
    }

    public IdentityClaims identityClaims() {
        if (identityClaims == null) {
            identityClaims = createLegacyClaims();
        }
        return identityClaims;
    }

    private IdentityClaims createLegacyClaims() {
        String identityPublicKey = childAsType(parsedPayload, "identityPublicKey", String.class);
        Map<?, ?> extraData = childAsType(parsedPayload, "extraData", Map.class);

        String displayName = childAsType(extraData, "displayName", String.class);
        String identityString = childAsType(extraData, "identity", String.class);
        String xuid = childAsType(extraData, "XUID", String.class);
        Object titleId = extraData.get("titleId");

        UUID identity;
        try {
            identity = UUID.fromString(identityString);
        } catch (Exception exception) {
            throw new IllegalStateException("identity node is an invalid UUID");
        }

        return new IdentityClaims(
                new IdentityData(displayName, identity, xuid, (String) titleId, null),
                identityPublicKey
        );
    }
}
