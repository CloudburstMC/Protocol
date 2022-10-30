package org.cloudburstmc.protocol.bedrock.codec.compat.serializer;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.nimbusds.jwt.SignedJWT;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginSerializerCompat implements BedrockPacketSerializer<LoginPacket> {
    public static final LoginSerializerCompat INSTANCE = new LoginSerializerCompat();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LoginPacket packet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LoginPacket packet) {
        packet.setProtocolVersion(buffer.readInt());

        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.

        Object json = JSONValue.parse(readString(jwt).array());
        checkArgument(json instanceof JSONObject && ((JSONObject) json).containsKey("chain"), "Invalid login chain");
        Object chain = ((JSONObject) json).get("chain");
        checkArgument(chain instanceof JSONArray, "Expected JSON array for login chain");

        try {
            for (Object node : (JSONArray) chain) {
                checkArgument(node instanceof String, "Expected String in login chain");
                packet.getChain().add(SignedJWT.parse((String) node));
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unable to decode JWT in login chain", e);
        }

        String value = (String) jwt.readCharSequence(jwt.readIntLE(), StandardCharsets.UTF_8);
        try {
            packet.setExtra(SignedJWT.parse(value));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unable to decode extra data", e);
        }
    }

    protected AsciiString readString(ByteBuf buffer) {
        return (AsciiString) buffer.readCharSequence(buffer.readIntLE(), StandardCharsets.US_ASCII);
    }
}
