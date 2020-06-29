package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelEventSerializer_v291 implements BedrockPacketSerializer<LevelEventPacket> {
    public static final LevelEventSerializer_v291 INSTANCE = new LevelEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventPacket packet) {
        VarInts.writeInt(buffer, helper.getLevelEventId(packet.getType()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventPacket packet) {
        int eventId = VarInts.readInt(buffer);
        packet.setType(helper.getLevelEvent(eventId));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setData(VarInts.readInt(buffer));
    }
}
