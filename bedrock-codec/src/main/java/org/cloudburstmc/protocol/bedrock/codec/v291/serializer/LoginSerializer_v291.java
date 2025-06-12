package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.auth.CertificateChainPayload;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.JSONValue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginSerializer_v291 implements BedrockPacketSerializer<LoginPacket> {
    public static final LoginSerializer_v291 INSTANCE = new LoginSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LoginPacket packet) {
        checkArgument(packet.getAuthPayload() instanceof CertificateChainPayload, "This client only supports CertificateChainPayload for login");
        buffer.writeInt(packet.getProtocolVersion());
        JSONArray array = new JSONArray();
        array.addAll(((CertificateChainPayload) packet.getAuthPayload()).getChain());

        JSONObject json = new JSONObject();
        json.put("chain", array);

        writeJwts(buffer, json.toJSONString(), packet.getClientJwt());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LoginPacket packet) {
        packet.setProtocolVersion(buffer.readInt());

        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.

        Object json = JSONValue.parse(readString(jwt));
        checkArgument(json instanceof JSONObject && ((JSONObject) json).containsKey("chain"), "Invalid login chain");
        Object chain = ((JSONObject) json).get("chain");
        checkArgument(chain instanceof JSONArray, "Expected JSON array for login chain");

        List<String> chainList = new ObjectArrayList<>(3);
        for (Object node : (JSONArray) chain) {
            checkArgument(node instanceof String, "Expected String in login chain");
            chainList.add((String) node);
        }
        packet.setAuthPayload(new CertificateChainPayload(chainList));

        String value = (String) jwt.readCharSequence(jwt.readIntLE(), StandardCharsets.UTF_8);
        packet.setClientJwt(value);
    }

    protected void writeJwts(ByteBuf buffer, String authJwt, String clientJwt) {
        int authLength = ByteBufUtil.utf8Bytes(authJwt);
        int clientLength = ByteBufUtil.utf8Bytes(clientJwt);

        VarInts.writeUnsignedInt(buffer, authLength + clientLength + 8);
        buffer.writeIntLE(authLength);
        buffer.writeCharSequence(authJwt, StandardCharsets.UTF_8);
        buffer.writeIntLE(clientLength);
        buffer.writeCharSequence(clientJwt, StandardCharsets.UTF_8);
    }

    protected String readString(ByteBuf buffer) {
        return (String) buffer.readCharSequence(buffer.readIntLE(), StandardCharsets.UTF_8);
    }
}
