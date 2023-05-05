package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import com.nimbusds.jose.shaded.gson.*;
import com.nimbusds.jwt.SignedJWT;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SubClientLoginPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubClientLoginSerializer_v291 implements BedrockPacketSerializer<SubClientLoginPacket> {
    public static final SubClientLoginSerializer_v291 INSTANCE = new SubClientLoginSerializer_v291();
    private final Gson gson = new Gson();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubClientLoginPacket packet) {
        JsonArray array = new JsonArray();
        for (SignedJWT node : packet.getChain()) {
            array.add(node.serialize());
        }
        String chainData = array.toString();
        int chainLength = ByteBufUtil.utf8Bytes(chainData);
        String extraData = packet.getExtra().serialize();
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

        JsonArray chain;
        try {
            JsonObject json = gson.fromJson(readString(jwt).toString(), JsonObject.class);
            chain = checkNotNull(json.getAsJsonArray("chain"));
        } catch (JsonSyntaxException | ClassCastException | NullPointerException exception) {
            throw new IllegalStateException("Expected JSON array for login chain");
        }

        try {
            for (JsonElement node : chain) {
                String chainNode;
                try {
                    chainNode = node.getAsString();
                } catch (ClassCastException | IllegalStateException exception) {
                    throw new IllegalStateException("Expected String in login chain");
                }
                packet.getChain().add(SignedJWT.parse(chainNode));
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
