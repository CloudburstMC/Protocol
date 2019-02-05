package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ExplodePacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExplodeSerializer_v332 implements PacketSerializer<ExplodePacket> {
    public static final ExplodeSerializer_v332 INSTANCE = new ExplodeSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, ExplodePacket packet) {
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, (int) (packet.getRadius() * 32));

        BedrockUtils.writeArray(buffer, packet.getRecords(), BedrockUtils::writeVector3i);
    }

    @Override
    public void deserialize(ByteBuf buffer, ExplodePacket packet) {
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setRadius(VarInts.readInt(buffer) / 32f);

        BedrockUtils.readArray(buffer, packet.getRecords(), BedrockUtils::readVector3i);
    }
}
