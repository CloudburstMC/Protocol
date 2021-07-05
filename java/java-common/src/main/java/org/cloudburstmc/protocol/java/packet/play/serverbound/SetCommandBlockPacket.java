package org.cloudburstmc.protocol.java.packet.play.serverbound;

import com.nukkitx.math.vector.Vector3i;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetCommandBlockPacket extends JavaPacket<JavaPlayPacketHandler> {
    private Vector3i position;
    private String command;
    private Mode mode;
    private boolean trackOutput;
    private boolean conditional;
    private boolean automatic;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.SET_COMMAND_MINECART;
    }

    public enum Mode {
        SEQUENCE,
        AUTO,
        REDSTONE;

        private static final Mode[] VALUES = values();

        public Mode getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
}
