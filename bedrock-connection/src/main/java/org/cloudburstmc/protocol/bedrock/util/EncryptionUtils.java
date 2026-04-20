package org.cloudburstmc.protocol.bedrock.util;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthPayload;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthType;
import org.cloudburstmc.protocol.bedrock.data.auth.CertificateChainPayload;
import org.cloudburstmc.protocol.bedrock.data.auth.TokenPayload;
import org.jose4j.json.JsonUtil;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.jwx.HeaderParameterNames;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.lang.JoseException;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.*;

@UtilityClass
public class EncryptionUtils {
    private static final ECPublicKey MOJANG_PUBLIC_KEY;

    /**
     * Microsoft's public key for verifying Education Edition server tokens,
     * retrieved from the MESS (Minecraft Education Server Services) endpoint:
     * {@code https://dedicatedserver.minecrafteduservices.com/public_keys/signing}
     * <p>
     * Used to verify the RSA signature on pipe-separated server tokens:
     * {@code tenantId|oid|expiry|signatureHex}
     * <p>
     * This key plays the same role for Education Edition that {@link #MOJANG_PUBLIC_KEY}
     * plays for standard Bedrock. Both are trust anchors for player identity.
     * Mojang's EC key verifies Xbox Live login chains; this RSA key verifies
     * education tokens containing the player's Entra tenant ID and Object ID.
     */
    private static final PublicKey EDUCATION_PUBLIC_KEY;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String MOJANG_PUBLIC_KEY_BASE64 =
            "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAECRXueJeTDqNRRgJi/vlRufByu/2G0i2Ebt6YMar5QX/R0DIIyrJMcUpruK4QveTfJSTp3Shlq4Gk34cD/4GUWwkv0DVuzeuB+tXija7HBxii03NHDbPAD0AKnLr2wdAp";
    private static final String EDUCATION_PUBLIC_KEY_BASE64 =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsFCr3nD8N3TJxJZ7Y4g1Z20Son+fUWTSd2f/XyIil2mGGGx/yjRj6l0ntbROsec8MZoaLsBG0nWm9/WhJcdXvJewbdd+mCyy7WXyYQgJcJPZP3kgBDySZMUnaowlUmR9gxRr+LevCafZKQwb19nwJB0EUt+nQsWBbTe2SuIdCqQIDAQAB";
    private static final KeyPairGenerator KEY_PAIR_GEN;

    public static final String ALGORITHM_TYPE = AlgorithmIdentifiers.ECDSA_USING_P384_CURVE_AND_SHA384;
    private static final AlgorithmConstraints ALGORITHM_CONSTRAINTS =
            new AlgorithmConstraints(ConstraintType.PERMIT, ALGORITHM_TYPE);

    private static final String DISCOVERY_ENDPOINT =
            "https://client.discovery.minecraft-services.net/api/v1.0/discovery/MinecraftPE/builds/1.0.0.0";
    private static final JSONParser JSON_PARSER = new JSONParser();

    private static final Map<String, Object> DISCOVERY_DATA = getDiscoveryData();
    private static final Map<String, Object> OPENID_CONFIGURATION = getOpenIdConfiguration();
    private static final String JWKS_URL = getJwksUrl();
    private static final String ISSUER = getIssuer();
    private static final HttpsJwks JWKS = new HttpsJwks(JWKS_URL);
    private static final HttpsJwksVerificationKeyResolver RESOLVER = new HttpsJwksVerificationKeyResolver(JWKS);
    private static final JwtConsumer MOJANG_CONSUMER = new JwtConsumerBuilder()
            .setVerificationKeyResolver(RESOLVER)
            .setRequireExpirationTime()
            .setRequireSubject()
            .setExpectedAudience(true, "api://auth-minecraft-services/multiplayer")
            .setExpectedIssuer(ISSUER)
            .build();

    private static final JwtConsumer OFFLINE_CONSUMER = new JwtConsumerBuilder()
            .setSkipAllValidators()
            .setSkipSignatureVerification()
            .setRequireExpirationTime()
            .setSkipDefaultAudienceValidation()
            .build();

    static {
        // DO NOT REMOVE THIS
        // Since Java 8u231, secp384r1 is deprecated and will throw an exception.
        String namedGroups = System.getProperty("jdk.tls.namedGroups");
        System.setProperty("jdk.tls.namedGroups", namedGroups == null || namedGroups.isEmpty() ? "secp384r1" : namedGroups + ", secp384r1");

        try {
            KEY_PAIR_GEN = KeyPairGenerator.getInstance("EC");
            KEY_PAIR_GEN.initialize(new ECGenParameterSpec("secp384r1"));
            MOJANG_PUBLIC_KEY = parseKey(MOJANG_PUBLIC_KEY_BASE64);
            EDUCATION_PUBLIC_KEY = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(EDUCATION_PUBLIC_KEY_BASE64)));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException e) {
            throw new AssertionError("Unable to initialize required encryption", e);
        }
    }

    private static Map<String, Object> getDiscoveryData() {
        try {
            URL url = new URL(DISCOVERY_ENDPOINT);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() != 200) {
                throw new IOException("Failed to fetch discovery data: " + connection.getResponseMessage());
            }
            try(InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                //noinspection unchecked
                return (Map<String, Object>) JSON_PARSER.parse(reader);
            }
        } catch (ParseException | IOException e) {
            throw new AssertionError("Unable to fetch discovery data from " + DISCOVERY_ENDPOINT, e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getAuthEnvironment() {
        Map<String, Object> result = (Map<String, Object>) DISCOVERY_DATA.get("result");

        if (result == null) {
            throw new AssertionError("Discovery data does not contain 'result' key" + DISCOVERY_DATA);
        }
        Map<String, Object> environments = (Map<String, Object>) result.get("serviceEnvironments");
        if (environments == null) {
            throw new AssertionError("Discovery data does not contain 'serviceEnvironments' key" + result);
        }
        Map<String, Object> authEnv = (Map<String, Object>) environments.get("auth");
        if (authEnv == null) {
            throw new AssertionError("Discovery data does not contain 'auth' environment" + environments);
        }
        Map<String, Object> prodEnv = (Map<String, Object>) authEnv.get("prod");
        if (prodEnv == null) {
            throw new AssertionError("Discovery data does not contain 'prod' environment" + authEnv);
        }
        return prodEnv;
    }

    private static String getServiceUri() {
        String issuer = (String) getAuthEnvironment().get("serviceUri");
        if (issuer == null) {
            throw new AssertionError("Discovery data does not contain 'issuer' key in 'prod' environment");
        }
        return issuer;
    }

    private static Map<String, Object> getOpenIdConfiguration() {
        String serviceUri = getServiceUri();

        String openIdConfigUrl = serviceUri + "/.well-known/openid-configuration";
        try {
            URL url = new URL(openIdConfigUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() != 200) {
                throw new IOException("Failed to fetch OpenID configuration: " + connection.getResponseMessage());
            }
            try (InputStream stream = connection.getInputStream();
                 InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                //noinspection unchecked
                return (Map<String, Object>) JSON_PARSER.parse(reader);
            }
        } catch (ParseException | IOException e) {
            throw new AssertionError("Unable to fetch OpenID configuration from " + openIdConfigUrl, e);
        }
    }

    private static String getJwksUrl() {
        String jwksUrl = (String) OPENID_CONFIGURATION.get("jwks_uri");
        if (jwksUrl == null || jwksUrl.isEmpty()) {
            throw new AssertionError("OpenID configuration does not contain 'jwks_uri' key: " + OPENID_CONFIGURATION);
        }
        return jwksUrl;
    }

    private static String getIssuer() {
        String issuer = (String) OPENID_CONFIGURATION.get("issuer");
        if (issuer == null || issuer.isEmpty()) {
            throw new AssertionError("OpenID configuration does not contain 'issuer' key: " + OPENID_CONFIGURATION);
        }
        return issuer;
    }

    /**
     * Generate EC public key from base 64 encoded string
     *
     * @param b64 base 64 encoded key
     * @return key generated
     * @throws NoSuchAlgorithmException runtime does not support the EC key spec
     * @throws InvalidKeySpecException  input does not conform with EC key spec
     */
    public static ECPublicKey parseKey(String b64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(b64)));
    }

    /**
     * Create EC key pair to be used for handshake and encryption
     *
     * @return EC KeyPair
     */
    public static KeyPair createKeyPair() {
        return KEY_PAIR_GEN.generateKeyPair();
    }

    public static byte[] verifyClientData(String clientDataJwt, String identityPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, JoseException {
        return verifyClientData(clientDataJwt, parseKey(identityPublicKey));
    }

    public static byte[] verifyClientData(String clientDataJwt, PublicKey identityPublicKey) throws JoseException {
        JsonWebSignature clientData = new JsonWebSignature();
        clientData.setCompactSerialization(clientDataJwt);
        clientData.setKey(identityPublicKey);
        if (!clientData.verifySignature()) {
            return null;
        }
        return clientData.getUnverifiedPayloadBytes();
    }

    public static ChainValidationResult validatePayload(AuthPayload payload)
            throws JoseException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidJwtException {
        ChainValidationResult result;

        if (payload instanceof CertificateChainPayload) {
            CertificateChainPayload chainPayload = (CertificateChainPayload) payload;
            List<String> chain = chainPayload.getChain();
            if (chain == null || chain.isEmpty()) {
                throw new IllegalStateException("Certificate chain is empty");
            }
            result = validateChain(chain);
        } else if (payload instanceof TokenPayload) {
            TokenPayload tokenPayload = (TokenPayload) payload;
            String token = tokenPayload.getToken();
            if (token == null || token.isEmpty()) {
                throw new IllegalStateException("Token is empty");
            }
            result = validateToken(payload.getAuthType(), token);
        } else {
            throw new IllegalArgumentException("Unsupported AuthPayload type: " + payload.getClass().getName());
        }

        if (payload.getAuthType() == AuthType.FULL) {
            if (result.identityClaims().extraData == null || result.identityClaims().extraData.xuid == null || result.identityClaims().extraData.displayName == null) {
                throw new IllegalStateException("Missing extraData for full auth");
            }
        }

        return result;
    }

    public static ChainValidationResult validateChain(List<String> chain)
            throws JoseException, NoSuchAlgorithmException, InvalidKeySpecException {
        switch (chain.size()) {
            case 1:
                // offline / proxied
                JsonWebSignature identity = new JsonWebSignature();
                identity.setCompactSerialization(chain.get(0));
                return new ChainValidationResult(false, identity.getUnverifiedPayload());
            case 3:
                ECPublicKey currentKey = null;
                Map<String, Object> parsedPayload = null;
                for (int i = 0; i < 3; i++) {
                    JsonWebSignature signature = new JsonWebSignature();
                    signature.setCompactSerialization(chain.get(i));

                    ECPublicKey expectedKey = parseKey(signature.getHeader(HeaderParameterNames.X509_URL));

                    if (currentKey == null) {
                        currentKey = expectedKey;
                    } else if (!currentKey.equals(expectedKey)) {
                        throw new IllegalStateException("Received broken chain");
                    }

                    signature.setAlgorithmConstraints(ALGORITHM_CONSTRAINTS);
                    signature.setKey(currentKey);
                    if (!signature.verifySignature()) {
                        throw new IllegalStateException("Chain signature doesn't match content");
                    }

                    // the second chain entry has to be signed by Mojang
                    if (i == 1 && !currentKey.equals(MOJANG_PUBLIC_KEY)) {
                        throw new IllegalStateException("The chain isn't signed by Mojang!");
                    }

                    parsedPayload = JsonUtil.parseJson(signature.getUnverifiedPayload());
                    String identityPublicKey = JsonUtils.childAsType(parsedPayload, "identityPublicKey", String.class);
                    currentKey = parseKey(identityPublicKey);
                }
                return new ChainValidationResult(true, parsedPayload);
            default:
                throw new IllegalStateException("Unexpected login chain length");
        }
    }

    public static ChainValidationResult validateToken(AuthType type, String token) throws InvalidJwtException, JoseException {
        if (type == AuthType.FULL || type == AuthType.GUEST) {
            JwtContext context = MOJANG_CONSUMER.process(token);
            return new ChainValidationResult(true, context);
        } else if (type == AuthType.SELF_SIGNED) {
            JwtContext context = OFFLINE_CONSUMER.process(token);
            return new ChainValidationResult(false, context);
        }
        throw new JoseException("Unsupported AuthType: " + type);
    }

    /**
     * Generate the secret key used to encrypt the connection
     *
     * @param localPrivateKey local private key
     * @param remotePublicKey remote public key
     * @param token           token generated or received from the server
     * @return secret key used to encrypt connection
     * @throws InvalidKeyException keys provided are not EC spec
     */
    public static SecretKey getSecretKey(PrivateKey localPrivateKey, PublicKey remotePublicKey, byte[] token) throws InvalidKeyException {
        byte[] sharedSecret = getEcdhSecret(localPrivateKey, remotePublicKey);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

        digest.update(token);
        digest.update(sharedSecret);
        byte[] secretKeyBytes = digest.digest();
        return new SecretKeySpec(secretKeyBytes, "AES");
    }

    private static byte[] getEcdhSecret(PrivateKey localPrivateKey, PublicKey remotePublicKey) throws InvalidKeyException {
        KeyAgreement agreement;
        try {
            agreement = KeyAgreement.getInstance("ECDH");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

        agreement.init(localPrivateKey);
        agreement.doPhase(remotePublicKey, true);
        return agreement.generateSecret();
    }

    /**
     * Create handshake JWS used in the {@link org.cloudburstmc.protocol.bedrock.packet.ServerToClientHandshakePacket}
     * which completes the encryption handshake.
     *
     * @param serverKeyPair used to sign the JWT
     * @param token         salt for the encryption handshake
     * @return signed JWS object
     * @throws JoseException invalid key pair provided
     */
    public static String createHandshakeJwt(KeyPair serverKeyPair, byte[] token) throws JoseException {
        return createHandshakeJwt(serverKeyPair, token, null);
    }

    /**
     * Create handshake JWS used in the {@link org.cloudburstmc.protocol.bedrock.packet.ServerToClientHandshakePacket}
     * which completes the encryption handshake.
     * <p>
     * For Education Edition clients, the {@code signedToken} parameter must contain the
     * education server token. Education clients verify this token during the handshake
     * and reject the connection if it is missing or invalid.
     *
     * @param serverKeyPair used to sign the JWT
     * @param token         salt for the encryption handshake
     * @param signedToken   education server token, or null for standard Bedrock
     * @return signed JWS object
     * @throws JoseException invalid key pair provided
     */
    public static String createHandshakeJwt(KeyPair serverKeyPair, byte[] token, String signedToken) throws JoseException {
        JsonWebSignature signature = new JsonWebSignature();
        signature.setAlgorithmHeaderValue(ALGORITHM_TYPE);
        signature.setHeader(
                HeaderParameterNames.X509_URL,
                Base64.getEncoder().encodeToString(serverKeyPair.getPublic().getEncoded())
        );
        signature.setKey(serverKeyPair.getPrivate());

        JwtClaims claims = new JwtClaims();
        claims.setClaim("salt", Base64.getEncoder().encodeToString(token));
        if (signedToken != null) {
            claims.setClaim("signedToken", signedToken);
        }
        signature.setPayload(claims.toJson());

        return signature.getCompactSerialization();
    }

    /**
     * Generate 16 bytes of random data for the handshake token using a {@link SecureRandom}
     *
     * @return 16 byte token
     */
    public static byte[] generateRandomToken() {
        byte[] token = new byte[16];
        SECURE_RANDOM.nextBytes(token);
        return token;
    }

    /**
     * Mojang's public key used to verify the JWT during login.
     *
     * @return Mojang's public EC key
     */
    public static ECPublicKey getMojangPublicKey() {
        return MOJANG_PUBLIC_KEY;
    }

    /**
     * Microsoft's public key used to verify Education Edition server tokens.
     *
     * @return Education RSA public key
     */
    public static PublicKey getEducationPublicKey() {
        return EDUCATION_PUBLIC_KEY;
    }

    /**
     * Validate the Education Edition login JWT end-to-end.
     * <p>
     * Education Edition clients send an outer self-signed JWT (the EduTokenChain) in place
     * of the standard Bedrock identity chain. Its payload carries a single {@code chain}
     * field containing the MESS-signed server token that actually proves player identity.
     * <p>
     * This method plays the same role for Education Edition that {@link #validatePayload}
     * plays for standard Bedrock. It is the top-level entry point: it unwraps the outer
     * education JWT via {@link #extractServerTokenFromEduTokenChain} and then verifies the
     * inner server token via {@link #validateEducationToken}.
     *
     * @param eduTokenChain the outer education login JWT as a compact serialization string
     * @return the validation result; {@link EducationTokenValidationResult.Status#INVALID}
     *         is returned if the JWT has no extractable server token
     * @throws JoseException the outer JWT is malformed or its payload is not valid JSON
     * @throws NoSuchAlgorithmException SHA256withRSA is not available
     * @throws InvalidKeyException the education public key is not a valid RSA key
     */
    public static EducationTokenValidationResult validateEducationPayload(String eduTokenChain)
            throws JoseException, NoSuchAlgorithmException, InvalidKeyException {
        String serverToken = extractServerTokenFromEduTokenChain(eduTokenChain);
        if (serverToken == null) {
            return EducationTokenValidationResult.invalid();
        }
        return validateEducationToken(serverToken);
    }

    /**
     * Extract the inner server token from an Education Edition login JWT (EduTokenChain).
     * <p>
     * The EduTokenChain payload contains a {@code chain} field whose value is the
     * pipe-separated MESS-signed server token ({@code tenantId|oid|expiry|signatureHex}).
     * This method peels the JWT structure and returns that value without verifying anything.
     * <p>
     * <b>Security:</b> the outer JWT's signature is intentionally NOT verified here.
     * Education login JWTs are self-signed with an ephemeral client key, so verifying
     * the outer signature only proves the client signed its own JWT and establishes
     * nothing about player identity. The inner {@code chain} field is the load-bearing
     * credential and carries its own MESS RSA signature, which is verified by
     * {@link #validateEducationToken}. Any caller that extracts additional fields from
     * the outer JWT must treat those fields as untrusted unless a separate verification
     * path is established for them.
     *
     * @param eduTokenChain the outer education login JWT as a compact serialization string
     * @return the inner server token, or {@code null} if the input is null/empty or the
     *         {@code chain} field is absent or not a string
     * @throws JoseException the input is not a valid JWT or its payload is not valid JSON
     */
    public static String extractServerTokenFromEduTokenChain(String eduTokenChain) throws JoseException {
        if (eduTokenChain == null || eduTokenChain.isEmpty()) {
            return null;
        }
        JsonWebSignature jws = new JsonWebSignature();
        jws.setCompactSerialization(eduTokenChain);
        Map<String, Object> payload = JsonUtil.parseJson(jws.getUnverifiedPayload());
        Object chain = payload.get("chain");
        return chain instanceof String ? (String) chain : null;
    }

    /**
     * Validate an education server token signed by Microsoft's MESS service.
     * <p>
     * Token format: {@code tenantId|oid|expiry|signatureHex} where the signature
     * is RSA PKCS#1 v1.5 SHA-256 over {@code tenantId|oid|expiry} as UTF-8 bytes.
     * <p>
     * This method plays the same role for Education Edition that
     * {@link #validateChain(List)} plays for standard Bedrock. It verifies player
     * identity against {@link #EDUCATION_PUBLIC_KEY} in the same manner that
     * {@code validateChain} verifies identity against {@link #MOJANG_PUBLIC_KEY}.
     * <p>
     * This inner MESS signature is the sole cryptographic anchor for education
     * player identity. Other apparent verification points in the education
     * login flow do not provide identity proof:
     * <ul>
     *   <li>The Bedrock login chain for education clients is a single self-signed
     *       JWT with an ephemeral per-session EC P-384 key. No Mojang root signs
     *       it, so {@link ChainValidationResult#signed()} returns false and the
     *       chain's claims prove only that the client holds the private half of
     *       a public key they themselves generated.</li>
     *   <li>The chain's {@code extraData.identity} is a client-generated
     *       per-session UUID, unrelated to the Entra Object ID and unanchored
     *       to any external authority.</li>
     *   <li>The {@code EduTokenChain} JWT that wraps this token in the login
     *       packet is itself signed with another client-generated key whose
     *       public half is embedded inline via the {@code x5u} header, not
     *       fetched from a remote trust anchor. Verifying the outer signature
     *       proves only that the client signed its own wrapper. An attacker
     *       who captures a valid inner token can re-wrap it with a fresh
     *       ephemeral key and produce a structurally valid outer JWT without
     *       Microsoft's involvement.</li>
     * </ul>
     * Only the inner signature over {@code tenantId|oid|expiry} binds the
     * token to a fixed Microsoft-controlled trust anchor
     * ({@link #EDUCATION_PUBLIC_KEY}).
     * Verifying it is the only mechanism that attests to player identity
     * against a party the client cannot impersonate.
     *
     * @param serverToken the pipe-separated education server token
     * @return validation result with status, tenant ID, OID, and expiry
     * @throws NoSuchAlgorithmException SHA256withRSA is not available
     * @throws InvalidKeyException the education public key is not a valid RSA key
     */
    public static EducationTokenValidationResult validateEducationToken(String serverToken)
            throws NoSuchAlgorithmException, InvalidKeyException {
        if (serverToken == null) {
            return EducationTokenValidationResult.invalid();
        }

        String[] parts = serverToken.split("\\|", -1);
        if (parts.length != 4) {
            return EducationTokenValidationResult.invalid();
        }

        byte[] signatureBytes;
        try {
            signatureBytes = hexToBytes(parts[3]);
        } catch (IllegalArgumentException e) {
            return EducationTokenValidationResult.invalid();
        }

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(EDUCATION_PUBLIC_KEY);
        try {
            sig.update((parts[0] + "|" + parts[1] + "|" + parts[2]).getBytes(StandardCharsets.UTF_8));
            if (!sig.verify(signatureBytes)) {
                return EducationTokenValidationResult.invalid();
            }
        } catch (SignatureException e) {
            return EducationTokenValidationResult.invalid();
        }

        // Mirrors the Education client: parsed_expiry < now is expired;
        // failed parse is also treated as expired (client forces value to 0).
        Instant expiry;
        try {
            expiry = Instant.parse(parts[2]);
        } catch (DateTimeParseException e) {
            return EducationTokenValidationResult.expired(parts[0], parts[1], Instant.EPOCH);
        }

        if (Instant.now().isAfter(expiry)) {
            return EducationTokenValidationResult.expired(parts[0], parts[1], expiry);
        }

        try {
            UUID.fromString(parts[0]);
            UUID.fromString(parts[1]);
        } catch (IllegalArgumentException e) {
            return EducationTokenValidationResult.invalid();
        }

        return EducationTokenValidationResult.valid(parts[0], parts[1], expiry);
    }

    private static byte[] hexToBytes(String hex) {
        if ((hex.length() & 1) != 0) {
            throw new IllegalArgumentException("odd-length hex string");
        }
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    public static Cipher createCipher(boolean gcm, boolean encrypt, SecretKey key) {
        try {
            byte[] iv;
            String transformation;
            if (gcm) {
                iv = new byte[16];
                System.arraycopy(key.getEncoded(), 0, iv, 0, 12);
                iv[15] = 2;
                transformation = "AES/CTR/NoPadding";
            } else {
                iv = Arrays.copyOf(key.getEncoded(), 16);
                transformation = "AES/CFB8/NoPadding";
            }
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new AssertionError("Unable to initialize required encryption", e);
        }
    }
}
