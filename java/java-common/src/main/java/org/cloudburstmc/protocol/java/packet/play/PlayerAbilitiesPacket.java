package org.cloudburstmc.protocol.java.packet.play;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.BidirectionalJavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.DirectionAvailability;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerAbilitiesPacket extends BidirectionalJavaPacket<JavaPlayPacketHandler> {
    private boolean flying;

    @DirectionAvailability(JavaPacketType.Direction.CLIENTBOUND)
    private boolean invulnerable;

    @DirectionAvailability(JavaPacketType.Direction.CLIENTBOUND)
    private boolean canFly;

    @DirectionAvailability(JavaPacketType.Direction.CLIENTBOUND)
    private boolean canInstantBuild;

    @DirectionAvailability(JavaPacketType.Direction.CLIENTBOUND)
    private float flyingSpeed;

    @DirectionAvailability(JavaPacketType.Direction.CLIENTBOUND)
    private float walkingSpeed;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.PLAYER_ABILITIES;
    }
}
