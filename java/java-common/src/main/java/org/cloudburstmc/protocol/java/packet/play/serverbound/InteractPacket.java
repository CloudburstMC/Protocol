package org.cloudburstmc.protocol.java.packet.play.serverbound;

import com.nukkitx.math.vector.Vector3f;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.data.Hand;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class InteractPacket extends JavaPacket<JavaPlayPacketHandler> {
    private int entityId;
    private Action action;
    private Vector3f mousePosition;
    private Hand hand;
    private boolean secondaryAction;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.INTERACT;
    }

    public enum Action {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        private static final Action[] VALUES = values();

        public static Action getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
}
