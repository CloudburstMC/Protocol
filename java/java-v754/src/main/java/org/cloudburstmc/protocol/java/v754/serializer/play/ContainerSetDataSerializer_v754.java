package org.cloudburstmc.protocol.java.v754.serializer.play;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.container.property.*;
import org.cloudburstmc.protocol.java.packet.play.ContainerSetDataPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerSetDataSerializer_v754 implements JavaPacketSerializer<ContainerSetDataPacket> {
    public static final ContainerSetDataSerializer_v754 INSTANCE = new ContainerSetDataSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, ContainerSetDataPacket packet) {
        buffer.writeByte(packet.getContainerId());

        int value = 0;
        if (packet.getRawProperty() instanceof FurnaceProperty) {
            value = ((FurnaceProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof EnchantmentTableProperty) {
            value = ((EnchantmentTableProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof BeaconProperty) {
            value = ((BeaconProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof AnvilProperty) {
            value = ((AnvilProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof BrewingStandProperty) {
            value = ((BrewingStandProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof StonecutterProperty) {
            value = ((StonecutterProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof LoomProperty) {
            value = ((LoomProperty) packet.getRawProperty()).ordinal();
        } else if (packet.getRawProperty() instanceof LecternProperty) {
            value = ((LecternProperty) packet.getRawProperty()).ordinal();
        }

        buffer.writeShort(value);

        buffer.writeShort(packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, ContainerSetDataPacket packet) {
        packet.setContainerId(buffer.readUnsignedByte());

        int data = buffer.readShort();

        packet.setValue(buffer.readShort());
    }
}
