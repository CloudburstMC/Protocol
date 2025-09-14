package org.cloudburstmc.protocol.bedrock.codec.v843.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.PlayerArmorDamageSerializer_v712;
import org.cloudburstmc.protocol.bedrock.data.PlayerArmorDamageFlag;
import org.cloudburstmc.protocol.bedrock.packet.PlayerArmorDamagePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerArmorDamageSerializer_v843 extends PlayerArmorDamageSerializer_v712 {

    public static final PlayerArmorDamageSerializer_v843 INSTANCE = new PlayerArmorDamageSerializer_v843();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerArmorDamagePacket packet) {
        helper.writeArray(buffer, packet.getFlags(), (buf, flag) -> {
            buf.writeByte(flag.ordinal() << 1);
            buf.writeShortLE(packet.getDamage()[flag.ordinal()]);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerArmorDamagePacket packet) {
        helper.readArray(buffer, packet.getFlags(), (buf, h) -> {
            int flag = buf.readUnsignedByte() >> 1;
            packet.getDamage()[flag] = buf.readShortLE();
            return PlayerArmorDamageFlag.values()[flag];
        });
    }
}
