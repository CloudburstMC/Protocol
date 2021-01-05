package org.cloudburstmc.protocol.java.v754.serializer.play;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.ContainerSetDataPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerSetDataSerializer_v754 implements JavaPacketSerializer<ContainerSetDataPacket> {
    public static final ContainerSetDataSerializer_v754 INSTANCE = new ContainerSetDataSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, ContainerSetDataPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeShort(packet.getRawProperty());
        buffer.writeShort(packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, ContainerSetDataPacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setRawProperty(buffer.readShort());
        packet.setValue(buffer.readShort());
    }
}
