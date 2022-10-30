package org.cloudburstmc.protocol.bedrock.codec.v554.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.GameTestResultsPacket;

public class GameTestResultsSerializer_v554 implements BedrockPacketSerializer<GameTestResultsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, GameTestResultsPacket packet) {
        buffer.writeBoolean(packet.isSuccessful());
        helper.writeString(buffer, packet.getError());
        helper.writeString(buffer, packet.getTestName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, GameTestResultsPacket packet) {
        packet.setSuccessful(buffer.readBoolean());
        packet.setError(helper.readString(buffer));
        packet.setTestName(helper.readString(buffer));
    }
}