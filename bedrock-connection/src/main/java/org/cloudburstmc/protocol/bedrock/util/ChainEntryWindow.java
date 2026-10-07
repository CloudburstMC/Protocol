package org.cloudburstmc.protocol.bedrock.util;

import lombok.experimental.UtilityClass;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

/**
 * Checks that a login chain entry is between its {@code nbf} and {@code exp}. Only the time claims
 * are read here, {@link EncryptionUtils#validateChain} verifies the signature.
 */
@UtilityClass
class ChainEntryWindow {
    static final int ALLOWED_CLOCK_SKEW_SECONDS = 60;

    private static final JwtConsumer CONSUMER = new JwtConsumerBuilder()
            .setSkipSignatureVerification()
            .setRequireExpirationTime()
            .setAllowedClockSkewInSeconds(ALLOWED_CLOCK_SKEW_SECONDS)
            .setSkipDefaultAudienceValidation()
            .build();

    static void check(String entry) {
        try {
            CONSUMER.processToClaims(entry);
        } catch (InvalidJwtException e) {
            throw new IllegalStateException("Chain entry is expired or not yet valid", e);
        }
    }
}
