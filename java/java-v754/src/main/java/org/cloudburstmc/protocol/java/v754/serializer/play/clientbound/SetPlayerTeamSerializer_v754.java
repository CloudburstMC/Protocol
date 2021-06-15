package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetPlayerTeamPacket;

import static org.cloudburstmc.protocol.java.packet.play.clientbound.SetPlayerTeamPacket.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetPlayerTeamSerializer_v754 implements JavaPacketSerializer<SetPlayerTeamPacket> {
    public static final SetPlayerTeamSerializer_v754 INSTANCE = new SetPlayerTeamSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetPlayerTeamPacket packet) throws PacketSerializeException {
        helper.writeString(buffer, packet.getName());
        buffer.writeByte(packet.getAction().ordinal());
        if (packet.getAction() == Action.CREATE || packet.getAction() == Action.UPDATE) {
            helper.writeComponent(buffer, packet.getDisplayName());
            buffer.writeByte((packet.isFriendlyFire() ? 0x1 : 0x0) | (packet.isSeeFriendlyInvisibles() ? 0x2 : 0x0));
            helper.writeString(buffer, packet.getNameTagVisibility().toString());
            helper.writeString(buffer, packet.getCollisionRule().toString());
            helper.writeVarInt(buffer, packet.getColor().ordinal());
            helper.writeComponent(buffer, packet.getPrefix());
            helper.writeComponent(buffer, packet.getSuffix());
        }

        if (packet.getAction() == Action.CREATE || packet.getAction() == Action.ADD_PLAYER || packet.getAction() == Action.REMOVE_PLAYER) {
            helper.writeArray(buffer, packet.getPlayers(), helper::writeString);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetPlayerTeamPacket packet) {
        packet.setName(helper.readString(buffer));
        packet.setAction(Action.getById(buffer.readByte()));
        if (packet.getAction() == Action.CREATE || packet.getAction() == Action.UPDATE) {
            packet.setDisplayName(helper.readComponent(buffer));
            byte flags = buffer.readByte();
            packet.setFriendlyFire((flags & 0x1) != 0);
            packet.setSeeFriendlyInvisibles((flags & 0x2) != 0);
            packet.setNameTagVisibility(NameTagVisibility.valueOf(helper.readString(buffer)));
            packet.setCollisionRule(CollisionRule.valueOf(helper.readString(buffer)));
            packet.setColor(TeamColor.getById(helper.readVarInt(buffer)));
            packet.setPrefix(helper.readComponent(buffer));
            packet.setSuffix(helper.readComponent(buffer));
        }

        if (packet.getAction() == Action.CREATE || packet.getAction() == Action.ADD_PLAYER || packet.getAction() == Action.REMOVE_PLAYER) {
            helper.readArray(buffer, packet.getPlayers(), helper::readString);
        }
    }
}
