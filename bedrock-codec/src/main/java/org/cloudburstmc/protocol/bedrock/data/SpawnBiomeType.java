package org.cloudburstmc.protocol.bedrock.data;

public enum SpawnBiomeType {
    DEFAULT,
    USER_DEFINED;
    
    private static final SpawnBiomeType[] VALUES = values();

    public static SpawnBiomeType byId(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        }
        throw new UnsupportedOperationException("Unknown SpawnBiomeType ID: " + id);
    }
}
