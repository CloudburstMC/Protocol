package org.cloudburstmc.protocol.bedrock.codec.compat.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.jose4j.json.JsonUtil;
import org.jose4j.lang.JoseException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

        try {
            Map<String, Object> json = JsonUtil.parseJson(readString(jwt).toString());
            checkArgument(json != null && json.containsKey("chain") && json.get("chain") instanceof List,
                    "Invalid login chain");
            List<?> chain = (List<?>) json.get("chain");

            for (Object node : chain) {
                checkArgument(node instanceof String, "Expected String in login certificate chain");
                packet.getCertificateChain().add((String) node);
            }

            String value = (String) jwt.readCharSequence(jwt.readIntLE(), StandardCharsets.UTF_8);
            packet.setClientJwt(value);
        } catch (JoseException e) {
            throw new IllegalArgumentException("Failed to parse auth payload", e);
        }
    }

    protected AsciiString readString(ByteBuf buffer) {
        return (AsciiString) buffer.readCharSequence(buffer.readIntLE(), StandardCharsets.US_ASCII);
    }
}
