package org.cloudburstmc.protocol.bedrock.util;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.ECGenParameterSpec;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChainEntryWindowTest {
    private static final long DAY = TimeUnit.DAYS.toSeconds(1);
    private static final KeyPair KEY = generateKey();

    private static KeyPair generateKey() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
            generator.initialize(new ECGenParameterSpec("secp384r1"));
            return generator.generateKeyPair();
        } catch (GeneralSecurityException e) {
            throw new AssertionError(e);
        }
    }

    /** An entry whose exp and nbf are the given seconds from now, or absent when null. */
    private static String entry(Long exp, Long nbf) throws JoseException {
        long now = NumericDate.now().getValue();
        JwtClaims claims = new JwtClaims();
        if (exp != null) {
            claims.setExpirationTime(NumericDate.fromSeconds(now + exp));
        }
        if (nbf != null) {
            claims.setNotBefore(NumericDate.fromSeconds(now + nbf));
        }
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P384_CURVE_AND_SHA384);
        jws.setKey(KEY.getPrivate());
        return jws.getCompactSerialization();
    }

    @Test
    void acceptsAnEntryInsideItsWindow() throws JoseException {
        String entry = entry(DAY, -DAY);
        assertDoesNotThrow(() -> ChainEntryWindow.check(entry));
    }

    @Test
    void rejectsAnExpiredEntry() throws JoseException {
        String entry = entry(-3 * DAY, -5 * DAY);
        assertThrows(IllegalStateException.class, () -> ChainEntryWindow.check(entry));
    }

    @Test
    void rejectsAnEntryThatIsNotValidYet() throws JoseException {
        String entry = entry(4 * DAY, 2 * DAY);
        assertThrows(IllegalStateException.class, () -> ChainEntryWindow.check(entry));
    }

    @Test
    void rejectsAnEntryWithoutExpiry() throws JoseException {
        String entry = entry(null, -DAY);
        assertThrows(IllegalStateException.class, () -> ChainEntryWindow.check(entry));
    }

    @Test
    void allowsForClockSkew() throws JoseException {
        String expired = entry(-ChainEntryWindow.ALLOWED_CLOCK_SKEW_SECONDS / 2L, -DAY);
        String early = entry(DAY, ChainEntryWindow.ALLOWED_CLOCK_SKEW_SECONDS / 2L);
        assertDoesNotThrow(() -> ChainEntryWindow.check(expired));
        assertDoesNotThrow(() -> ChainEntryWindow.check(early));
    }
}
