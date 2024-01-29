package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;

public interface CompressionStrategy {

    BatchCompression getCompression(BedrockBatchWrapper wrapper);

    BatchCompression getCompression(CompressionAlgorithm algorithm);

    BatchCompression getDefaultCompression();
}
