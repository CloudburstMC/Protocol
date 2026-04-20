package org.cloudburstmc.protocol.bedrock.util;

import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Instant;

/**
 * Result of verifying a Minecraft Education Edition server token.
 * <p>
 * Education tokens are the education equivalent of Mojang-signed login chains for
 * standard Bedrock. They prove a player's identity within a Microsoft 365
 * Education tenant via an RSA signature from Microsoft's education services (MESS).
 * <p>
 * Token format: {@code tenantId|oid|expiry|signatureHex}
 * <dl>
 *   <dt>{@code tenantId}</dt>
 *   <dd>M365 Education tenant UUID identifying the school or district.</dd>
 *   <dt>{@code oid}</dt>
 *   <dd>Entra Object ID UUID. Unique per user per tenant and immutable.</dd>
 *   <dt>{@code expiry}</dt>
 *   <dd>ISO 8601 UTC timestamp after which the token is no longer valid.</dd>
 *   <dt>{@code signatureHex}</dt>
 *   <dd>RSA PKCS#1 v1.5 SHA-256 signature encoded as lowercase hex.</dd>
 * </dl>
 */
@ToString
public final class EducationTokenValidationResult {

    public enum Status {
        /** Signature is valid and the token has not expired. */
        VALID,
        /** Signature is valid but the expiry timestamp is in the past. */
        EXPIRED,
        /** Token is null, malformed, or the signature did not verify. */
        INVALID
    }

    private final Status status;
    private final String tenantId;
    private final String oid;
    private final Instant expiry;

    private EducationTokenValidationResult(Status status, String tenantId, String oid, Instant expiry) {
        this.status = status;
        this.tenantId = tenantId;
        this.oid = oid;
        this.expiry = expiry;
    }

    static EducationTokenValidationResult valid(String tenantId, String oid, Instant expiry) {
        return new EducationTokenValidationResult(Status.VALID, tenantId, oid, expiry);
    }

    static EducationTokenValidationResult expired(String tenantId, String oid, Instant expiry) {
        return new EducationTokenValidationResult(Status.EXPIRED, tenantId, oid, expiry);
    }

    static EducationTokenValidationResult invalid() {
        return new EducationTokenValidationResult(Status.INVALID, null, null, null);
    }

    public Status getStatus() {
        return status;
    }

    /**
     * The M365 Education tenant ID (school/district).
     * Only available when status is {@link Status#VALID} or {@link Status#EXPIRED}.
     */
    public @Nullable String getTenantId() {
        return tenantId;
    }

    /**
     * The Entra Object ID of the user. Unique per user per tenant, immutable,
     * and cryptographically verified via the education token signature.
     * Only available when status is {@link Status#VALID} or {@link Status#EXPIRED}.
     */
    public @Nullable String getOid() {
        return oid;
    }

    /**
     * The token's expiry timestamp.
     * Only available when status is {@link Status#VALID} or {@link Status#EXPIRED}.
     */
    public @Nullable Instant getExpiry() {
        return expiry;
    }
}
