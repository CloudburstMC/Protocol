package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.HurtArmorPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HurtArmorSerializer_v291 implements BedrockPacketSerializer<HurtArmorPacket> {
    public static final HurtArmorSerializer_v291 INSTANCE = new HurtArmorSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, HurtArmorPacket packet) {
        VarInts.writeInt(buffer, packet.getDamage());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, HurtArmorPacket packet) {
        packet.setDamage(VarInts.readInt(buffer));
    }
}
