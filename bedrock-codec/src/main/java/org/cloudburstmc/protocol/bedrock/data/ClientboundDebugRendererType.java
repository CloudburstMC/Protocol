package org.cloudburstmc.protocol.bedrock.data;

import java.util.HashMap;
import java.util.Map;

public enum ClientboundDebugRendererType {
    INVALID("invalid"),
    CLEAR_DEBUG_MARKERS("cleardebugmarkers"),
    ADD_DEBUG_MARKER_CUBE("adddebugmarkercube");

    private static final Map<String, ClientboundDebugRendererType> serializeNames = new HashMap<>(values().length, 1);
    static {
        for (ClientboundDebugRendererType value : values()) {
            serializeNames.put(value.getSerializeName(), value);
        }
    }

    private final String serializeName;

    ClientboundDebugRendererType(String serializeName) {
        this.serializeName = serializeName;
    }

    /**
     * The name the type is sent as from v898, when the type became a string.
     */
    public String getSerializeName() {
        return this.serializeName;
    }

    public static ClientboundDebugRendererType fromName(String serializeName) {
        return serializeNames.get(serializeName);
    }
}
