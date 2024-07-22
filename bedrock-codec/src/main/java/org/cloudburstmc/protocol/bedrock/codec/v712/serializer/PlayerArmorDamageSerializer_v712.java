package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.PlayerArmorDamageSerializer_v407;
import org.cloudburstmc.protocol.bedrock.data.PlayerArmorDamageFlag;
import org.cloudburstmc.protocol.bedrock.packet.PlayerArmorDamagePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Set;

public class PlayerArmorDamageSerializer_v712 extends PlayerArmorDamageSerializer_v407 {
    public static final PlayerArmorDamageSerializer_v712 INSTANCE = new PlayerArmorDamageSerializer_v712();

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerArmorDamagePacket packet) {
        int flagsVal = buffer.readUnsignedByte();
        Set<PlayerArmorDamageFlag> flags = packet.getFlags();
        int[] damage = packet.getDamage();
        for (int i = 0; i < FLAGS.length; i++) {
            if ((flagsVal & (1 << i)) != 0) {
                flags.add(FLAGS[i]);
                damage[i] = VarInts.readInt(buffer);
            }
        }
    }
}