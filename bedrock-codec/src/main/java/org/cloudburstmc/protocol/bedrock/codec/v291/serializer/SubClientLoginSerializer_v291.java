package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SubClientLoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.JSONValue;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import java.nio.charset.StandardCharsets;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubClientLoginSerializer_v291 implements BedrockPacketSerializer<SubClientLoginPacket> {
    public static final SubClientLoginSerializer_v291 INSTANCE = new SubClientLoginSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubClientLoginPacket packet) {
        JSONArray array = new JSONArray();
        array.addAll(packet.getChain());

        JSONObject json = new JSONObject();
        json.put("chain", array);

        String chainData = json.toJSONString();
        int chainLength = ByteBufUtil.utf8Bytes(chainData);
        String extraData = packet.getExtra();
        int extraLength = ByteBufUtil.utf8Bytes(extraData);

        VarInts.writeUnsignedInt(buffer, chainLength + extraLength + 8);
        buffer.writeIntLE(chainLength);
        buffer.writeCharSequence(chainData, StandardCharsets.US_ASCII);
        buffer.writeIntLE(extraLength);
        buffer.writeCharSequence(extraData, StandardCharsets.US_ASCII);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubClientLoginPacket packet) {
        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.

        try {
            Object json = JSONValue.parseWithException(readString(jwt).toString());
            checkArgument(json instanceof JSONObject && ((JSONObject) json).containsKey("chain"), "Invalid login chain");

            Object chain = ((JSONObject) json).get("chain");
            checkArgument(chain instanceof JSONArray, "Expected JSON array for login chain");

            for (Object node : (JSONArray) chain) {
                checkArgument(node instanceof String, "Expected String in login chain");
                packet.getChain().add((String) node);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unable to decode JWT in login chain", e);
        }

        String value = (String) jwt.readCharSequence(jwt.readIntLE(), StandardCharsets.UTF_8);
        packet.setExtra(value);
    }

    protected AsciiString readString(ByteBuf buffer) {
        return (AsciiString) buffer.readCharSequence(buffer.readIntLE(), StandardCharsets.US_ASCII);
    }
}
