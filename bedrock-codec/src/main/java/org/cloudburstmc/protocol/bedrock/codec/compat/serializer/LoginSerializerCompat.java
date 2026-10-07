package org.cloudburstmc.protocol.bedrock.codec.compat.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.LoginLimits;
import org.cloudburstmc.protocol.bedrock.data.auth.CertificateChainPayload;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.JSONValue;

import java.nio.charset.StandardCharsets;
import java.util.List;

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

        String authJwt = LoginLimits.readAuthPayload(jwt, StandardCharsets.US_ASCII);
        LoginLimits.checkJsonDepth(authJwt);
        Object json = JSONValue.parse(authJwt);
        checkArgument(json instanceof JSONObject && ((JSONObject) json).containsKey("chain"), "Invalid login chain");
        Object chain = ((JSONObject) json).get("chain");
        checkArgument(chain instanceof JSONArray, "Expected JSON array for login chain");

        List<String> chainList = new ObjectArrayList<>(3);
        for (Object node : (JSONArray) chain) {
            checkArgument(node instanceof String, "Expected String in login chain");
            chainList.add((String) node);
        }
        packet.setAuthPayload(new CertificateChainPayload(chainList));

        packet.setClientJwt(LoginLimits.readClientJwt(jwt));
    }
}
