package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

public interface CompressionCodec {

    String NAME = "compression-codec";

    int getLevel();

    void setLevel(int level);
}
