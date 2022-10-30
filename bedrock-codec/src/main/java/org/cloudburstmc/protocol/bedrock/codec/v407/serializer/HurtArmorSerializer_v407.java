package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.HurtArmorPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HurtArmorSerializer_v407 implements BedrockPacketSerializer<HurtArmorPacket> {
    public static final HurtArmorSerializer_v407 INSTANCE = new HurtArmorSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, HurtArmorPacket packet) {
        VarInts.writeInt(buffer, packet.getCause());
        VarInts.writeInt(buffer, packet.getDamage());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, HurtArmorPacket packet) {
        packet.setCause(VarInts.readInt(buffer));
        packet.setDamage(VarInts.readInt(buffer));
    }
}
