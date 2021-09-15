package org.cloudburstmc.protocol.bedrock.data;

public enum PhotoType {
    PORTFOLIO,
    PHOTO_ITEM,
    BOOK;

    private static final PhotoType[] VALUES = values();

    public static PhotoType from(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : null;
    }
}
