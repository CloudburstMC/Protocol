package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.Hand;
import org.cloudburstmc.protocol.java.packet.play.serverbound.SwingPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SwingPacketSerializer_v754 implements JavaPacketSerializer<SwingPacket> {
    public static final SwingPacketSerializer_v754 INSTANCE = new SwingPacketSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SwingPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getHand().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SwingPacket packet) throws PacketSerializeException {
        packet.setHand(Hand.getById(helper.readVarInt(buffer)));
    }
}
