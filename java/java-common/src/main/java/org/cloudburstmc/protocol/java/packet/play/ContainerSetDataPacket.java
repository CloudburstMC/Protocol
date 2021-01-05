package org.cloudburstmc.protocol.java.packet.play;

import lombok.*;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.data.window.property.WindowProperty;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
public class ContainerSetDataPacket extends JavaPacket<JavaPlayPacketHandler> {
    private int windowId;
    private int rawProperty;
    private int value;

    public ContainerSetDataPacket(int windowId, WindowProperty rawProperty, int value) {
        this(windowId, Integer.parseInt(rawProperty.toString()), value);
    }

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPlayPacketType getPacketType() {
        return JavaPlayPacketType.CONTAINER_SET_DATA_S2C;
    }
}
