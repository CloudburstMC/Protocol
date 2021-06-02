package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.PlayerLookAtPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerLookAtSerializer_v754 implements JavaPacketSerializer<PlayerLookAtPacket> {
    public static final PlayerLookAtSerializer_v754 INSTANCE = new PlayerLookAtSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlayerLookAtPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getFromAnchor().ordinal());
        helper.writePosition(buffer, packet.getPosition());
        buffer.writeBoolean(packet.isAtEntity());
        if (packet.isAtEntity()) {
            helper.writeVarInt(buffer, packet.getEntityId());
            helper.writeVarInt(buffer, packet.getToAnchor().ordinal());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlayerLookAtPacket packet) throws PacketSerializeException {
        packet.setFromAnchor(PlayerLookAtPacket.Anchor.getById(helper.readVarInt(buffer)));
        packet.setPosition(helper.readPosition(buffer));
        packet.setAtEntity(buffer.readBoolean());
        if (packet.isAtEntity()) {
            packet.setEntityId(helper.readVarInt(buffer));
            packet.setToAnchor(PlayerLookAtPacket.Anchor.getById(helper.readVarInt(buffer)));
        }
    }
}
