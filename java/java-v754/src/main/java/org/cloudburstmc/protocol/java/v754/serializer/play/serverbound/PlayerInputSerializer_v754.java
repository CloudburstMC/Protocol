package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.PlayerInputPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerInputSerializer_v754 implements JavaPacketSerializer<PlayerInputPacket> {
    public static final PlayerInputSerializer_v754 INSTANCE = new PlayerInputSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlayerInputPacket packet) throws PacketSerializeException {
        helper.writeVector2f(buffer, packet.getInput());
        byte flags = 0;
        if (packet.isJumping()) {
            flags |= 0x01;
        }
        if (packet.isSneaking()) {
            flags |= 0x02;
        }
        buffer.writeByte(flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlayerInputPacket packet) throws PacketSerializeException {
        packet.setInput(helper.readVector2f(buffer));
        byte flags = buffer.readByte();
        packet.setJumping((flags & 0x01) == 0x01);
        packet.setSneaking((flags & 0x02) == 0x02);
    }
}
