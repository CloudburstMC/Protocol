package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SnappyCompressionTest {

    @Test
    public void roundTrip() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInboundHandlerAdapter());
        ChannelHandlerContext ctx = channel.pipeline().firstContext();
        SnappyCompression snappy = new SnappyCompression();

        byte[] payload = new byte[100_000];
        Random random = new Random(42);
        for (int i = 0; i < payload.length; i++) {
            // Runs of repeated bytes so the compressor emits copies, not just literals.
            payload[i] = (byte) (i % 512 < 256 ? random.nextInt(256) : i / 512);
        }

        ByteBuf compressed = snappy.encode(ctx, Unpooled.wrappedBuffer(payload));
        ByteBuf decompressed = snappy.decode(ctx, compressed);
        try {
            assertArrayEquals(payload, ByteBufUtil.getBytes(decompressed));
        } finally {
            compressed.release();
            decompressed.release();
            channel.finishAndReleaseAll();
        }
    }
}
