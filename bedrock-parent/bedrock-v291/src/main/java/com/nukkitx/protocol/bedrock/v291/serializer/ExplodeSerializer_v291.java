package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ExplodePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExplodeSerializer_v291 implements BedrockPacketSerializer<ExplodePacket> {
    public static final ExplodeSerializer_v291 INSTANCE = new ExplodeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ExplodePacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, (int) (packet.getRadius() * 32));

        helper.writeArray(buffer, packet.getRecords(), helper::writeVector3i);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ExplodePacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRadius(VarInts.readInt(buffer) / 32f);

        helper.readArray(buffer, packet.getRecords(), helper::readVector3i);
    }
}
