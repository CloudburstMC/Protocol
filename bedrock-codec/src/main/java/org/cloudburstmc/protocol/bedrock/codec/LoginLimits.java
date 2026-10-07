package org.cloudburstmc.protocol.bedrock.codec;

import io.netty.buffer.ByteBuf;
import org.jose4j.json.JsonUtil;
import org.jose4j.lang.JoseException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

/**
 * Bounds for reading Login and SubClientLogin.
 */
public final class LoginLimits {
    /**
     * Longest auth payload (the chain or token JSON) in bytes. Real ones are a few KB.
     */
    public static final int MAX_AUTH_PAYLOAD_LENGTH = Integer.getInteger("bedrock.maxLoginAuthLength", 128 * 1024);
    /**
     * Longest client data JWT in bytes, the same as the batch decompression limit. Persona skins make real ones
     * several hundred KB.
     */
    public static final int MAX_CLIENT_JWT_LENGTH = Integer.getInteger("bedrock.maxDecompressedBytes", 10 * 1024 * 1024);
    /**
     * Deepest nesting of objects and arrays in the auth payload. Real ones nest two levels.
     */
    public static final int MAX_JSON_DEPTH = 8;
    /**
     * Longest encoded header of the client data JWT. Real ones are about 250 characters.
     */
    public static final int MAX_JWT_HEADER_LENGTH = 4096;

    private LoginLimits() {
    }

    public static String readAuthPayload(ByteBuf buffer, Charset charset) {
        return readBounded(buffer, charset, MAX_AUTH_PAYLOAD_LENGTH, "Auth payload");
    }

    public static String readClientJwt(ByteBuf buffer) {
        return readBounded(buffer, StandardCharsets.UTF_8, MAX_CLIENT_JWT_LENGTH, "Client data");
    }

    /**
     * Parses auth payload JSON after checking its nesting depth.
     */
    public static Map<String, Object> parseJson(String json) throws JoseException {
        checkJsonDepth(json);
        return JsonUtil.parseJson(json);
    }

    public static void checkJsonDepth(CharSequence json) {
        int depth = 0;
        boolean inString = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (inString) {
                if (c == '\\') {
                    i++;
                } else if (c == '"') {
                    inString = false;
                }
            } else if (c == '"') {
                inString = true;
            } else if (c == '[' || c == '{') {
                checkArgument(++depth <= MAX_JSON_DEPTH, "Login JSON nests deeper than %s levels", MAX_JSON_DEPTH);
            } else if ((c == ']' || c == '}') && depth > 0) {
                depth--;
            }
        }
    }

    public static void checkJwtHeader(String jwt) {
        checkArgument(jwt.indexOf('.') <= MAX_JWT_HEADER_LENGTH, "JWT header is longer than %s characters", MAX_JWT_HEADER_LENGTH);
    }

    private static String readBounded(ByteBuf buffer, Charset charset, int maxLength, String what) {
        int length = buffer.readIntLE();
        checkArgument(length >= 0 && length <= maxLength, "%s length %s is out of bounds", what, length);
        return buffer.readCharSequence(length, charset).toString();
    }
}
