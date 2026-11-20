package org.cloudburstmc.protocol.bedrock.data.camera;

import java.util.HashMap;
import java.util.Map;

public enum CameraEase {
    LINEAR(0, "linear"),
    SPRING(1, "spring"),
    EASE_IN_SINE(14, "in_sine"),
    EASE_OUT_SINE(15, "out_sine"),
    EASE_IN_OUT_SINE(16, "in_out_sine"),
    EASE_IN_QUAD(2, "in_quad"),
    EASE_OUT_QUAD(3, "out_quad"),
    EASE_IN_OUT_QUAD(4, "in_out_quad"),
    EASE_IN_CUBIC(5, "in_cubic"),
    EASE_OUT_CUBIC(6, "out_cubic"),
    EASE_IN_OUT_CUBIC(7, "in_out_cubic"),
    EASE_IN_QUART(8, "in_quart"),
    EASE_OUT_QUART(9, "out_quart"),
    EASE_IN_OUT_QUART(10, "in_out_quart"),
    EASE_IN_QUINT(11, "in_quint"),
    EASE_OUT_QUINT(12, "out_quint"),
    EASE_IN_OUT_QUINT(13, "in_out_quint"),
    EASE_IN_EXPO(17, "in_expo"),
    EASE_OUT_EXPO(18, "out_expo"),
    EASE_IN_OUT_EXPO(19, "in_out_expo"),
    EASE_IN_CIRC(20, "in_circ"),
    EASE_OUT_CIRC(21, "out_circ"),
    EASE_IN_OUT_CIRC(22, "in_out_circ"),
    EASE_IN_BACK(26, "in_back"),
    EASE_OUT_BACK(27, "out_back"),
    EASE_IN_OUT_BACK(28, "in_out_back"),
    EASE_IN_ELASTIC(29, "in_elastic"),
    EASE_OUT_ELASTIC(30, "out_elastic"),
    EASE_IN_OUT_ELASTIC(31, "in_out_elastic"),
    EASE_IN_BOUNCE(23, "in_bounce"),
    EASE_OUT_BOUNCE(24, "out_bounce"),
    EASE_IN_OUT_BOUNCE(25, "in_out_bounce");

    private static final Map<String, CameraEase> serializeNames = new HashMap<>(values().length, 1);
    private static final CameraEase[] byId = new CameraEase[values().length];
    static {
        for (CameraEase value : values()) {
            serializeNames.put(value.getSerializeName(), value);
            byId[value.getId()] = value;
        }
    }

    private final int id;
    private final String serializeName;

    CameraEase(int id, String serializeName) {
        this.id = id;
        this.serializeName = serializeName;
    }

    /**
     * The number the client knows this ease by when it is sent as a byte. It follows the protocol's
     * easing_function enum, not the order of these constants.
     */
    public int getId() {
        return this.id;
    }

    public String getSerializeName() {
        return this.serializeName;
    }

    public static CameraEase byId(int id) {
        return id >= 0 && id < byId.length ? byId[id] : null;
    }

    public static CameraEase fromName(String serializeName) {
        return serializeNames.get(serializeName);
    }
}
