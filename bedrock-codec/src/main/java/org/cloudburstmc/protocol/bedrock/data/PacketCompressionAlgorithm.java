package org.cloudburstmc.protocol.bedrock.data;

import lombok.Getter;

@Getter
public enum PacketCompressionAlgorithm implements CompressionAlgorithm {
    ZLIB(0),
    SNAPPY(1),
    NONE(255);

    private final int networkId;

    PacketCompressionAlgorithm(int networkId) {
        this.networkId = networkId;
    }
}