package org.cloudburstmc.protocol.java.packet.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAdvancementsTabSerializer_v754 implements JavaPacketSerializer<SelectAdvancementsTabPacket> {
    public static final SelectAdvancementsTabSerializer_v754 INSTANCE = new SelectAdvancementsTabSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SelectAdvancementsTabPacket packet) throws PacketSerializeException {
        helper.writeOptional(buffer, packet.getTab(), helper::writeKey);
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SelectAdvancementsTabPacket packet) throws PacketSerializeException {
        packet.setTab(helper.readOptional(buffer, helper::readKey));
    }
}
