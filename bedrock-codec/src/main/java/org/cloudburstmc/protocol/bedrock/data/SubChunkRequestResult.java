package org.cloudburstmc.protocol.bedrock.data;

public enum SubChunkRequestResult {
    UNDEFINED,
    SUCCESS,
    CHUNK_NOT_FOUND,
    INVALID_DIMENSION,
    PLAYER_NOT_FOUND,
    INDEX_OUT_OF_BOUNDS,
    SUCCESS_ALL_AIR
}
