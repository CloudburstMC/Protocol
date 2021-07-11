package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.Direction;
import org.cloudburstmc.protocol.java.data.Hand;
import org.cloudburstmc.protocol.java.packet.play.serverbound.UseItemOnPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UseItemOnSerializer_v754 implements JavaPacketSerializer<UseItemOnPacket> {
    public static final UseItemOnSerializer_v754 INSTANCE = new UseItemOnSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, UseItemOnPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getHand().ordinal());
        helper.writeBlockPosition(buffer, packet.getPosition());
        helper.writeVarInt(buffer, packet.getDirection().ordinal());
        helper.writeVector3f(buffer, packet.getMousePosition());
        buffer.writeBoolean(packet.isInsideBlock());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, UseItemOnPacket packet) throws PacketSerializeException {
        packet.setHand(Hand.getById(helper.readVarInt(buffer)));
        packet.setPosition(helper.readBlockPosition(buffer));
        packet.setDirection(Direction.getById(helper.readVarInt(buffer)));
        packet.setMousePosition(helper.readVector3f(buffer));
        packet.setInsideBlock(buffer.readBoolean());
    }
}
