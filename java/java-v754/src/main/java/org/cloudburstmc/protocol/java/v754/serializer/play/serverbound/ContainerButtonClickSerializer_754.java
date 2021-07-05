package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.ContainerButtonClickPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerButtonClickSerializer_754 implements JavaPacketSerializer<ContainerButtonClickPacket> {
    public static final ContainerButtonClickSerializer_754 INSTANCE = new ContainerButtonClickSerializer_754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, ContainerButtonClickPacket packet) throws PacketSerializeException {
        buffer.writeByte(packet.getContainerId());
        buffer.writeByte(packet.getButtonId());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, ContainerButtonClickPacket packet) throws PacketSerializeException {
        packet.setContainerId(buffer.readByte());
        packet.setButtonId(buffer.readByte());
    }
}
