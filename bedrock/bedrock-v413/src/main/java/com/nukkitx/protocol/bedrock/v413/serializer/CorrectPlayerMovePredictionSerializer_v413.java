package com.nukkitx.protocol.bedrock.v413.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CorrectPlayerMovePredictionSerializer_v413 implements BedrockPacketSerializer<CorrectPlayerMovePredictionPacket> {

    public static final CorrectPlayerMovePredictionSerializer_v413 INSTANCE = new CorrectPlayerMovePredictionSerializer_v413();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CorrectPlayerMovePredictionPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getDelta());
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CorrectPlayerMovePredictionPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setDelta(helper.readVector3f(buffer));
        packet.setOnGround(buffer.readBoolean());
        packet.setTick(VarInts.readUnsignedInt(buffer));
    }
}
