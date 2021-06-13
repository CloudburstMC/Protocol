package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetObjectivePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetObjectiveSerializer_v754 implements JavaPacketSerializer<SetObjectivePacket> {
    public static final SetObjectiveSerializer_v754 INSTANCE = new SetObjectiveSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetObjectivePacket packet) throws PacketSerializeException {
        helper.writeString(buffer, packet.getObjectiveName());
        buffer.writeByte(packet.getAction().ordinal());
        if (packet.getAction().ordinal() == 0 || packet.getAction().ordinal() == 2) {
            helper.writeComponent(buffer, packet.getDisplayName());
            helper.writeVarInt(buffer, packet.getRenderType().ordinal());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetObjectivePacket packet) throws PacketSerializeException {
        packet.setObjectiveName(helper.readString(buffer));
        byte action = buffer.readByte();
        packet.setAction(SetObjectivePacket.Action.getById(action));
        if (action == 0 || action == 2) {
            packet.setDisplayName(helper.readComponent(buffer));
            packet.setRenderType(SetObjectivePacket.RenderType.getById(helper.readVarInt(buffer)));
        }
    }
}
