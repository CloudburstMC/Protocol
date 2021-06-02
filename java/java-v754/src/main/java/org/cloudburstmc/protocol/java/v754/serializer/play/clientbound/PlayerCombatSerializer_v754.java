package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.CombatEvent;
import org.cloudburstmc.protocol.java.packet.play.clientbound.PlayerCombatPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerCombatSerializer_v754 implements JavaPacketSerializer<PlayerCombatPacket> {
    public static final PlayerCombatSerializer_v754 INSTANCE = new PlayerCombatSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlayerCombatPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getCombatEvent().ordinal());
        if (packet.getCombatEvent() == CombatEvent.EXIT_COMBAT) {
            helper.writeVarInt(buffer, packet.getDuration());
            buffer.writeInt(packet.getKillerEntityId());
        } else if (packet.getCombatEvent() == CombatEvent.ENTITY_DIED) {
            helper.writeVarInt(buffer, packet.getPlayerEntityId());
            buffer.writeInt(packet.getKillerEntityId());
            helper.writeComponent(buffer, packet.getMessage());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlayerCombatPacket packet) throws PacketSerializeException {
        packet.setCombatEvent(CombatEvent.getById(helper.readVarInt(buffer)));
        if (packet.getCombatEvent() == CombatEvent.EXIT_COMBAT) {
            packet.setDuration(helper.readVarInt(buffer));
            packet.setKillerEntityId(buffer.readInt());
        } else if (packet.getCombatEvent() == CombatEvent.ENTITY_DIED) {
            packet.setPlayerEntityId(helper.readVarInt(buffer));
            packet.setKillerEntityId(buffer.readInt());
            packet.setMessage(helper.readComponent(buffer));
        }
    }
}
