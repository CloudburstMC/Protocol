package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EntityLinkData {
    private final long from;
    private final long to;
    private final Type type;
    private final boolean immediate;
    private final boolean riderInitiated;
    /**
     * @since v712
     */
    private final float vehicleAngularVelocity;

    @Deprecated
    public EntityLinkData(long from, long to, Type type, boolean immediate) {
        this(from, to, type, immediate, false, 0f);
    }

    @Deprecated
    public EntityLinkData(long from, long to, Type type, boolean immediate, boolean riderInitiated) {
        this(from, to, type, immediate, riderInitiated, 0f);
    }

    public enum Type {
        REMOVE,
        RIDER,
        PASSENGER;

        private static final Type[] VALUES = values();

        public static Type byId(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            }
            throw new UnsupportedOperationException("Unknown EntityLinkData.Type ID: " + id);
        }
    }
}
