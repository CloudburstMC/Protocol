package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;

public interface CompressionCodec {

    String NAME = "compression-codec";

    int getLevel();

    void setLevel(int level);

    PacketCompressionAlgorithm getAlgorithm();
}
