package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ChangeDimensionPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeDimensionSerializer_v354 implements PacketSerializer<ChangeDimensionPacket> {
    public static final ChangeDimensionSerializer_v354 INSTANCE = new ChangeDimensionSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, ChangeDimensionPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        buffer.writeBoolean(packet.isRespawn());
    }

    @Override
    public void deserialize(ByteBuf buffer, ChangeDimensionPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setRespawn(buffer.readBoolean());
    }
}
