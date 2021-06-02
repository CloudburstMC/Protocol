package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.PlayerAbilitiesPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAbilitiesSerializer_v754 implements JavaPacketSerializer<PlayerAbilitiesPacket> {
    public static final PlayerAbilitiesSerializer_v754 INSTANCE = new PlayerAbilitiesSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlayerAbilitiesPacket packet) throws PacketSerializeException {
        byte abilities = 0;
        if (packet.isInvulnerable()) abilities |= 0x01;
        if (packet.isFlying()) abilities |= 0x02;
        if (packet.isCanFly()) abilities |= 0x04;
        if (packet.isCanInstantBuild()) abilities |= 0x08;
        buffer.writeByte(abilities);
        buffer.writeFloat(packet.getFlyingSpeed());
        buffer.writeFloat(packet.getWalkingSpeed());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlayerAbilitiesPacket packet) throws PacketSerializeException {
        byte abilities = buffer.readByte();
        packet.setInvulnerable((abilities & 0x01) != 0);
        packet.setFlying((abilities & 0x02) != 0);
        packet.setCanFly((abilities & 0x04) != 0);
        packet.setCanInstantBuild((abilities & 0x08) != 0);
        packet.setFlyingSpeed(buffer.readFloat());
        packet.setWalkingSpeed(buffer.readFloat());
    }
}
