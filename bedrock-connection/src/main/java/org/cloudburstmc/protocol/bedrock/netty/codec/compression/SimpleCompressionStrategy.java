package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;

public class SimpleCompressionStrategy implements CompressionStrategy {
    private final BatchCompression compression;

    public SimpleCompressionStrategy(BatchCompression compression) {
        this.compression = compression;
    }

    @Override
    public BatchCompression getCompression(BedrockBatchWrapper wrapper) {
        return this.compression;
    }

    @Override
    public BatchCompression getCompression(CompressionAlgorithm algorithm) {
        return this.compression;
    }

    @Override
    public BatchCompression getDefaultCompression() {
        return this.compression;
    }
}
