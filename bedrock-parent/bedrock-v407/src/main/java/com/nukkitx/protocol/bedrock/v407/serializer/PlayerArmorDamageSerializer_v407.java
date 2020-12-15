package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.PlayerArmorDamageFlag;
import com.nukkitx.protocol.bedrock.packet.PlayerArmorDamagePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerArmorDamageSerializer_v407 implements BedrockPacketSerializer<PlayerArmorDamagePacket> {
    public static final PlayerArmorDamageSerializer_v407 INSTANCE = new PlayerArmorDamageSerializer_v407();

    private static final PlayerArmorDamageFlag[] FLAGS = PlayerArmorDamageFlag.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerArmorDamagePacket packet) {
        int flags = 0;
        for (PlayerArmorDamageFlag flag : packet.getFlags()) {
            flags |= 1 << flag.ordinal();
        }
        buffer.writeByte(flags);

        int[] damage = packet.getDamage();

        for (PlayerArmorDamageFlag flag : packet.getFlags()) {
            int value = damage[flag.ordinal()];
            VarInts.writeInt(buffer, value);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerArmorDamagePacket packet) {
        int flagsVal = buffer.readUnsignedByte();
        Set<PlayerArmorDamageFlag> flags = packet.getFlags();
        int[] damage = packet.getDamage();
        for (int i = 0; i < 4; i++) {
            if ((flagsVal & (1 << i)) != 0) {
                flags.add(FLAGS[i]);
                damage[i] = VarInts.readInt(buffer);
            }
        }
    }
}
