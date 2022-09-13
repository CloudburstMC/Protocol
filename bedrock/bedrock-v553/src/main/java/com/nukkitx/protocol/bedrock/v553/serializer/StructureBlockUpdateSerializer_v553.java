package com.nukkitx.protocol.bedrock.v553.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v388.serializer.StructureBlockUpdateSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureBlockUpdateSerializer_v553 extends StructureBlockUpdateSerializer_v388 {

    public static final StructureBlockUpdateSerializer_v553 INSTANCE = new StructureBlockUpdateSerializer_v553();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isWaterlogged());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setWaterlogged(buffer.readBoolean());
    }
}
