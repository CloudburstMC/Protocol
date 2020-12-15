package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ChangeDimensionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeDimensionSerializer_v291 implements BedrockPacketSerializer<ChangeDimensionPacket> {
    public static final ChangeDimensionSerializer_v291 INSTANCE = new ChangeDimensionSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ChangeDimensionPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3f(buffer, packet.getPosition());
        buffer.writeBoolean(packet.isRespawn());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ChangeDimensionPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRespawn(buffer.readBoolean());
    }
}
