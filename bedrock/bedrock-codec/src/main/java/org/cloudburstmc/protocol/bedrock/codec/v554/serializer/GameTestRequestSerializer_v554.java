package org.cloudburstmc.protocol.bedrock.codec.v554.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.GameTestRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class GameTestRequestSerializer_v554 implements BedrockPacketSerializer<GameTestRequestPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, GameTestRequestPacket packet) {
        VarInts.writeInt(buffer, packet.getMaxTestsPerBatch());
        VarInts.writeInt(buffer, packet.getRepeatCount());
        buffer.writeByte(packet.getRotation());
        buffer.writeBoolean(packet.isStoppingOnFailure());
        helper.writeVector3i(buffer, packet.getTestPos());
        VarInts.writeInt(buffer, packet.getTestsPerRow());
        helper.writeString(buffer, packet.getTestName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, GameTestRequestPacket packet) {
        packet.setMaxTestsPerBatch(VarInts.readInt(buffer));
        packet.setRepeatCount(VarInts.readInt(buffer));
        packet.setRotation(buffer.readByte());
        packet.setStoppingOnFailure(buffer.readBoolean());
        packet.setTestPos(helper.readVector3i(buffer));
        packet.setTestsPerRow(VarInts.readInt(buffer));
        packet.setTestName(helper.readString(buffer));
    }
}