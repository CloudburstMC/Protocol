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
public class SetStructureBlockPacket extends JavaPacket<JavaPlayPacketHandler> {
    private Vector3i position;
    private UpdateType updateType;
    private StructureMode structureMode;
    private String name;
    private Vector3i offset;
    private Vector3i size;
    private Mirror structureMirror;
    private Rotation structureRotation;
    private String data;
    private float integrity;
    private long seed;
    private boolean ignoreEntities;
    private boolean showAir;
    private boolean showBoundingBox;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.SET_STRUCTURE_BLOCK;
    }

    public enum Mirror {
        NONE,
        LEFT_RIGHT,
        FRONT_BACK;

        public static final Mirror[] VALUES = values();

        public static Mirror getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }

    public enum Rotation {
        NONE,
        CLOCKWISE_90,
        CLOCKWISE_180,
        COUNTERCLOCKWISE_90;

        public static final Rotation[] VALUES = values();

        public static Rotation getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }

    public enum StructureMode {
        SAVE,
        LOAD,
        CORNER,
        DATA;

        public static final StructureMode[] VALUES = values();

        public static StructureMode getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
    public enum UpdateType {
        UPDATE_DATA,
        SAVE_AREA,
        LOAD_AREA,
        SCAN_AREA;

        public static final UpdateType[] VALUES = values();

        public static UpdateType getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
}
