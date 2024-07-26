package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.PlayerArmorDamageFlag;
import org.cloudburstmc.protocol.bedrock.packet.PlayerArmorDamagePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerArmorDamageSerializer_v407 implements BedrockPacketSerializer<PlayerArmorDamagePacket> {
    public static final PlayerArmorDamageSerializer_v407 INSTANCE = new PlayerArmorDamageSerializer_v407();

    protected static final PlayerArmorDamageFlag[] FLAGS = PlayerArmorDamageFlag.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerArmorDamagePacket packet) {
        int flags = 0;
        for (PlayerArmorDamageFlag flag : packet.getFlags()) {
            int ordinal = flag.ordinal();
            if (ordinal > this.getMaxFlagIndex()) {
                continue;
            }
            flags |= 1 << ordinal;
        }
        buffer.writeByte(flags);

        int[] damage = packet.getDamage();

        for (PlayerArmorDamageFlag flag : packet.getFlags()) {
            int value = damage[flag.ordinal()];
            VarInts.writeInt(buffer, value);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerArmorDamagePacket packet) {
        int flagsVal = buffer.readUnsignedByte();
        Set<PlayerArmorDamageFlag> flags = packet.getFlags();
        int[] damage = packet.getDamage();
        for (int i = 0; i <= this.getMaxFlagIndex(); i++) {
            if ((flagsVal & (1 << i)) != 0) {
                flags.add(FLAGS[i]);
                damage[i] = VarInts.readInt(buffer);
            }
        }
    }

    protected int getMaxFlagIndex() {
        return 3;
    }
}
