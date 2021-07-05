package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.RenameItemPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RenameItemSerializer_v754 implements JavaPacketSerializer<RenameItemPacket> {
    public static final RenameItemSerializer_v754 INSTANCE = new RenameItemSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, RenameItemPacket packet) throws PacketSerializeException {
        helper.writeString(buffer, packet.getName());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, RenameItemPacket packet) throws PacketSerializeException {
        packet.setName(helper.readString(buffer));
    }
}
