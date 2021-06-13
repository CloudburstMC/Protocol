package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetEntityMotionPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityMotionSerializer_v754 implements JavaPacketSerializer<SetEntityMotionPacket> {
    public static final SetEntityMotionSerializer_v754 INSTANCE = new SetEntityMotionSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetEntityMotionPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        helper.writeVelocity(buffer, packet.getMotion());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetEntityMotionPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        packet.setMotion(helper.readVelocity(buffer));
    }
}
