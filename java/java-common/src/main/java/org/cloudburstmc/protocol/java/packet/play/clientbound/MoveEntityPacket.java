package org.cloudburstmc.protocol.java.packet.play.clientbound;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3d;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public abstract class MoveEntityPacket extends JavaPacket<JavaPlayPacketHandler> {
    private int entityId;
    private boolean onGround;

    public abstract Type getType();

    @Data
    @EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
    public static class Pos extends MoveEntityPacket {
        private Vector3d delta = Vector3d.ZERO;

        @Override
        public boolean handle(JavaPlayPacketHandler handler) {
            return handler.handle(this);
        }

        @Override
        public Type getType() {
            return Type.POS;
        }

        @Override
        public JavaPacketType getPacketType() {
            return JavaPlayPacketType.MOVE_ENTITY_POS;
        }
    }

    @Data
    @EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
    public static class PosRot extends MoveEntityPacket {
        private Vector3d delta = Vector3d.ZERO;
        private Vector2f rotation = Vector2f.ZERO;

        @Override
        public boolean handle(JavaPlayPacketHandler handler) {
            return handler.handle(this);
        }

        @Override
        public Type getType() {
            return Type.POS_ROT;
        }

        @Override
        public JavaPacketType getPacketType() {
            return JavaPlayPacketType.MOVE_ENTITY_POS_ROT;
        }
    }

    @Data
    @EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
    public static class Rot extends MoveEntityPacket {
        private Vector2f rotation = Vector2f.ZERO;

        @Override
        public boolean handle(JavaPlayPacketHandler handler) {
            return handler.handle(this);
        }

        @Override
        public Type getType() {
            return Type.ROT;
        }

        @Override
        public JavaPacketType getPacketType() {
            return JavaPlayPacketType.MOVE_ENTITY_ROT;
        }
    }

    public enum Type {
        POS,
        POS_ROT,
        ROT;
    }
}
