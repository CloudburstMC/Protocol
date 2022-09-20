package com.nukkitx.protocol.bedrock.v554.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.GameTestRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameTestRequestSerializer_v554 implements BedrockPacketSerializer<GameTestRequestPacket> {

    public static final GameTestRequestSerializer_v554 INSTANCE = new GameTestRequestSerializer_v554();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GameTestRequestPacket packet) {
        VarInts.writeInt(buffer, packet.getMaxTestsPerBatch());
        VarInts.writeInt(buffer, packet.getRepeatCount());
        buffer.writeByte(packet.getRotation());
        buffer.writeBoolean(packet.isStoppingOnFailure());
        helper.writeVector3i(buffer, packet.getTestPos());
        VarInts.writeInt(buffer, packet.getTestsPerRow());
        helper.writeString(buffer, packet.getTestName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GameTestRequestPacket packet) {
        packet.setMaxTestsPerBatch(VarInts.readInt(buffer));
        packet.setRepeatCount(VarInts.readInt(buffer));
        packet.setRotation(buffer.readByte());
        packet.setStoppingOnFailure(buffer.readBoolean());
        packet.setTestPos(helper.readVector3i(buffer));
        packet.setTestsPerRow(VarInts.readInt(buffer));
        packet.setTestName(helper.readString(buffer));
    }
}
