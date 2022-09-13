package com.nukkitx.protocol.bedrock.v553.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.GameTestResultsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameTestResultsSerializer_v553 implements BedrockPacketSerializer<GameTestResultsPacket> {

    public static final GameTestResultsSerializer_v553 INSTANCE = new GameTestResultsSerializer_v553();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GameTestResultsPacket packet) {
        buffer.writeBoolean(packet.isSuccessful());
        helper.writeString(buffer, packet.getError());
        helper.writeString(buffer, packet.getTestName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GameTestResultsPacket packet) {
        packet.setSuccessful(buffer.readBoolean());
        packet.setError(helper.readString(buffer));
        packet.setTestName(helper.readString(buffer));
    }
}
