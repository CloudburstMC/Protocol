package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.LoginLimits;
import org.cloudburstmc.protocol.bedrock.codec.compat.serializer.LoginSerializerCompat;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LoginSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.SubClientLoginSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v818.Bedrock_v818;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.LoginSerializer_v818;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.SubClientLoginSerializer_v818;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthPayload;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthType;
import org.cloudburstmc.protocol.bedrock.data.auth.CertificateChainPayload;
import org.cloudburstmc.protocol.bedrock.data.auth.TokenPayload;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.bedrock.packet.SubClientLoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.jose4j.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginSerializationTest {
    private static final BedrockCodecHelper HELPER = Bedrock_v818.CODEC.createHelper();
    // Real chain entries are about 1 KB, and a persona skin makes the client data about 600 KB
    private static final List<String> CHAIN = Arrays.asList(jwt(1000), jwt(1000), jwt(1000));
    private static final String CLIENT_JWT = jwt(600_000);

    @Test
    public void realisticLoginsRoundTrip() {
        assertRoundTrip(LoginSerializer_v291.INSTANCE, new CertificateChainPayload(CHAIN));
        assertRoundTrip(LoginSerializer_v818.INSTANCE, new CertificateChainPayload(CHAIN, AuthType.FULL));
        assertRoundTrip(LoginSerializer_v818.INSTANCE, new TokenPayload(jwt(2000), AuthType.FULL));

        SubClientLoginPacket subClient = new SubClientLoginPacket();
        subClient.setAuthPayload(new CertificateChainPayload(CHAIN, AuthType.FULL));
        subClient.setClientJwt(CLIENT_JWT);
        ByteBuf buffer = Unpooled.buffer();
        SubClientLoginSerializer_v818.INSTANCE.serialize(buffer, HELPER, subClient);
        SubClientLoginPacket read = new SubClientLoginPacket();
        SubClientLoginSerializer_v818.INSTANCE.deserialize(buffer, HELPER, read);
        assertEquals(CHAIN, ((CertificateChainPayload) read.getAuthPayload()).getChain());
        assertEquals(CLIENT_JWT, read.getClientJwt());
    }

    @Test
    public void refusesAnAuthPayloadOverTheLimit() {
        ByteBuf blob = Unpooled.buffer();
        blob.writeIntLE(LoginLimits.MAX_AUTH_PAYLOAD_LENGTH + 1);
        blob.writeBytes(new byte[16]);
        assertThrows(IllegalArgumentException.class, () -> decode(LoginSerializer_v818.INSTANCE, blob));
    }

    @Test
    public void refusesClientDataOverTheLimit() {
        ByteBuf blob = Unpooled.buffer();
        writeString(blob, JsonUtil.toJson(Collections.singletonMap("chain", CHAIN)));
        blob.writeIntLE(LoginLimits.MAX_CLIENT_JWT_LENGTH + 1);
        blob.writeBytes(new byte[16]);
        assertThrows(IllegalArgumentException.class, () -> decode(LoginSerializer_v291.INSTANCE, blob));
    }

    @Test
    public void refusesDeepAuthJson() {
        // Next to a valid chain or token, so nothing but the nesting is wrong
        String chain = padded(JsonUtil.toJson(Collections.singletonMap("chain", CHAIN)));

        // v818, in the outer object and in the certificate it carries as a string
        assertRefused(LoginSerializer_v818.INSTANCE, padded(authJson("", jwt(1000))));
        assertRefused(LoginSerializer_v818.INSTANCE, authJson(chain, ""));
        assertRefused(SubClientLoginSerializer_v818.INSTANCE, authJson(chain, ""));

        assertRefused(LoginSerializer_v291.INSTANCE, chain);
        assertRefused(SubClientLoginSerializer_v291.INSTANCE, chain);
        assertRefused(LoginSerializerCompat.INSTANCE, chain);
    }

    @Test
    public void countsOnlyNestingOutsideStrings() {
        assertDoesNotThrow(() -> LoginLimits.checkJsonDepth("{\"a\":\"[[[[[[[[[[\\\"[[[[[[[[[[\",\"b\":[]}"));
        assertThrows(IllegalArgumentException.class,
                () -> LoginLimits.checkJsonDepth("]]]]]]]]]]" + nested(LoginLimits.MAX_JSON_DEPTH + 1)));
    }

    @Test
    public void refusesALongClientDataHeader() {
        assertDoesNotThrow(() -> LoginLimits.checkJwtHeader(CLIENT_JWT));
        assertThrows(IllegalArgumentException.class,
                () -> LoginLimits.checkJwtHeader(repeat("e", LoginLimits.MAX_JWT_HEADER_LENGTH + 1) + ".e30.sig"));
    }

    private static void assertRoundTrip(BedrockPacketSerializer<LoginPacket> serializer, AuthPayload payload) {
        LoginPacket packet = new LoginPacket();
        packet.setProtocolVersion(Bedrock_v818.CODEC.getProtocolVersion());
        packet.setAuthPayload(payload);
        packet.setClientJwt(CLIENT_JWT);
        ByteBuf buffer = Unpooled.buffer();
        serializer.serialize(buffer, HELPER, packet);

        LoginPacket read = new LoginPacket();
        serializer.deserialize(buffer, HELPER, read);
        if (payload instanceof CertificateChainPayload) {
            assertEquals(((CertificateChainPayload) payload).getChain(), ((CertificateChainPayload) read.getAuthPayload()).getChain());
        } else {
            assertEquals(((TokenPayload) payload).getToken(), ((TokenPayload) read.getAuthPayload()).getToken());
        }
        assertEquals(CLIENT_JWT, read.getClientJwt());
    }

    private static void assertRefused(BedrockPacketSerializer<?> serializer, String authJson) {
        ByteBuf blob = Unpooled.buffer();
        writeString(blob, authJson);
        writeString(blob, CLIENT_JWT);
        assertThrows(IllegalArgumentException.class, () -> decode(serializer, blob));
    }

    @SuppressWarnings("unchecked")
    private static void decode(BedrockPacketSerializer<?> serializer, ByteBuf blob) {
        boolean subClient = serializer instanceof SubClientLoginSerializer_v291;
        ByteBuf buffer = Unpooled.buffer();
        if (!subClient) {
            buffer.writeInt(Bedrock_v818.CODEC.getProtocolVersion());
        }
        VarInts.writeUnsignedInt(buffer, blob.readableBytes());
        buffer.writeBytes(blob);
        if (subClient) {
            ((BedrockPacketSerializer<SubClientLoginPacket>) serializer).deserialize(buffer, HELPER, new SubClientLoginPacket());
        } else {
            ((BedrockPacketSerializer<LoginPacket>) serializer).deserialize(buffer, HELPER, new LoginPacket());
        }
    }

    private static void writeString(ByteBuf buffer, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        buffer.writeIntLE(bytes.length);
        buffer.writeBytes(bytes);
    }

    private static String authJson(String certificate, String token) {
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("AuthenticationType", 0);
        json.put("Certificate", certificate);
        json.put("Token", token);
        return JsonUtil.toJson(json);
    }

    /**
     * Adds a key holding arrays nested to the limit, which inside the object makes one level too many.
     */
    private static String padded(String json) {
        return json.substring(0, json.length() - 1) + ",\"pad\":" + nested(LoginLimits.MAX_JSON_DEPTH) + "}";
    }

    private static String nested(int depth) {
        return repeat("[", depth) + repeat("]", depth);
    }

    /**
     * Shaped like a real ES384 JWT: a header of about 250 characters, the payload, and a 128 character signature.
     */
    private static String jwt(int length) {
        return repeat("h", 250) + "." + repeat("p", Math.max(1, length - 380)) + "." + repeat("s", 128);
    }

    private static String repeat(String value, int times) {
        StringBuilder builder = new StringBuilder(value.length() * times);
        for (int i = 0; i < times; i++) {
            builder.append(value);
        }
        return builder.toString();
    }
}
