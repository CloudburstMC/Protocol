package org.cloudburstmc.protocol.bedrock.data;

public enum PacketCompressionAlgorithm implements CompressionAlgorithm {
    ZLIB,
    SNAPPY,
    NONE
}