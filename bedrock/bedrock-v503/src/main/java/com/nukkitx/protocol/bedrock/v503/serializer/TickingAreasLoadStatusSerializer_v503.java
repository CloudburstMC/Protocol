package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.TickingAreasLoadStatusPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TickingAreasLoadStatusSerializer_v503 implements BedrockPacketSerializer<TickingAreasLoadStatusPacket> {
    public static final TickingAreasLoadStatusSerializer_v503 INSTANCE = new TickingAreasLoadStatusSerializer_v503();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TickingAreasLoadStatusPacket packet) {
        buffer.writeBoolean(packet.isWaitingForPreload());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TickingAreasLoadStatusPacket packet) {
        packet.setWaitingForPreload(buffer.readBoolean());
    }
}