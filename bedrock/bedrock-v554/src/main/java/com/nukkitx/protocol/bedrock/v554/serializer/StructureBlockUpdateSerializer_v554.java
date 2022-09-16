package com.nukkitx.protocol.bedrock.v554.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v388.serializer.StructureBlockUpdateSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureBlockUpdateSerializer_v554 extends StructureBlockUpdateSerializer_v388 {

    public static final StructureBlockUpdateSerializer_v554 INSTANCE = new StructureBlockUpdateSerializer_v554();

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
