package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetEntityLinkPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityLinkSerializer_v754 implements JavaPacketSerializer<SetEntityLinkPacket> {
    public static final SetEntityLinkSerializer_v754 INSTANCE = new SetEntityLinkSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetEntityLinkPacket packet) throws PacketSerializeException {
        buffer.writeInt(packet.getEntityId());
        buffer.writeInt(packet.getAttachId());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetEntityLinkPacket packet) throws PacketSerializeException {
        packet.setEntityId(buffer.readInt());
        packet.setAttachId(buffer.readInt());
    }
}
