package org.cloudburstmc.protocol.java.packet.play;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.BidirectionalJavaPacket;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.data.world.Difficulty;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.DirectionAvailability;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ChangeDifficultyPacket extends BidirectionalJavaPacket<JavaPlayPacketHandler> {
    private Difficulty difficulty;

    @DirectionAvailability(JavaPacketType.Direction.CLIENTBOUND)
    private boolean locked;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.CHANGE_DIFFICULTY;
    }
}
