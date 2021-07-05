package org.cloudburstmc.protocol.java.packet.play.serverbound;

import com.nukkitx.math.vector.Vector2f;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerInputPacket extends JavaPacket<JavaPlayPacketHandler> {
    private Vector2f input;
    private boolean jumping;
    private boolean sneaking;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.PLAYER_INPUT;
    }
}
