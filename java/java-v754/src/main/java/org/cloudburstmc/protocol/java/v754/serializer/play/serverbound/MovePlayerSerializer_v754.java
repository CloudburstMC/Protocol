package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.MovePlayerPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovePlayerSerializer_v754  implements JavaPacketSerializer<MovePlayerPacket> {
    public static final MovePlayerSerializer_v754 INSTANCE = new MovePlayerSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, MovePlayerPacket packet) throws PacketSerializeException {
        helper.writePosition(buffer, packet.getPosition());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, MovePlayerPacket packet) throws PacketSerializeException {
        packet.setPosition(helper.readPosition(buffer));
        packet.setOnGround(buffer.readBoolean());
    }
}
