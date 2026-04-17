package org.cloudburstmc.protocol.bedrock.util;

import lombok.ToString;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@ToString
public final class IdentityClaims {
    public final IdentityData extraData;
    public final String identityPublicKey;
    private PublicKey parsedIdentityPublicKey;

    public IdentityClaims(IdentityData extraData, String identityPublicKey) {
        this.extraData = extraData;
        this.identityPublicKey = identityPublicKey;
    }

    public PublicKey parsedIdentityPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (parsedIdentityPublicKey == null) {
            parsedIdentityPublicKey = EncryptionUtils.parseKey(identityPublicKey);
        }
        return parsedIdentityPublicKey;
    }
}
