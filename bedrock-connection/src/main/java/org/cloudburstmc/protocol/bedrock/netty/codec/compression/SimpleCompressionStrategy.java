package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.common.util.Zlib;

/**
 * A simple compression strategy that uses the same compression for all packets, but
 * supports decompression using all Bedrock protocol supported algorithms.
 */
public class SimpleCompressionStrategy implements CompressionStrategy {
    private final BatchCompression compression;

    private final BatchCompression none;
    private final BatchCompression zlib;
    private final BatchCompression snappy;

    public SimpleCompressionStrategy(BatchCompression compression) {
        this.compression = compression;
        this.none = new NoopCompression();

        if (compression.getAlgorithm() == PacketCompressionAlgorithm.ZLIB) {
            this.zlib = compression;
            this.snappy = new SnappyCompression();
        } else if (compression.getAlgorithm() == PacketCompressionAlgorithm.SNAPPY) {
            this.zlib = new ZlibCompression(Zlib.RAW);
            this.snappy = compression;
        } else {
            this.zlib = new ZlibCompression(Zlib.RAW);
            this.snappy = new SnappyCompression();
        }
    }

    @Override
    public BatchCompression getCompression(BedrockBatchWrapper wrapper) {
        return this.compression;
    }

    @Override
    public BatchCompression getCompression(CompressionAlgorithm algorithm) {
        if (algorithm == PacketCompressionAlgorithm.ZLIB) {
            return this.zlib;
        } else if (algorithm == PacketCompressionAlgorithm.SNAPPY) {
            return this.snappy;
        } else if (algorithm == PacketCompressionAlgorithm.NONE) {
            return this.none;
        }
        return this.compression;
    }

    @Override
    public BatchCompression getDefaultCompression() {
        return this.compression;
    }
}
