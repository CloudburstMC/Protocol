package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CorruptedFrameException;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BedrockBatchDecoderTest {

    @Test
    public void splitsPacketsUpToTheLimit() {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchDecoder(3));
        try {
            channel.writeInbound(batch(1, 2, 3));
            for (int length = 1; length <= 3; length++) {
                ByteBuf packet = channel.readInbound();
                try {
                    assertEquals(length, packet.readableBytes());
                } finally {
                    packet.release();
                }
            }
            assertNull(channel.readInbound());
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    @Test
    public void refusesBatchOverTheLimit() {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchDecoder(3));
        try {
            assertThrows(CorruptedFrameException.class, () -> channel.writeInbound(batch(1, 1, 1, 1)));
            assertNull(channel.readInbound());
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    @Test
    public void refusesEmptyPacket() {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchDecoder(3));
        try {
            assertThrows(CorruptedFrameException.class, () -> channel.writeInbound(batch(1, 0)));
            assertNull(channel.readInbound());
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    @Test
    public void refusesLengthPastTheBatch() {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchDecoder(3));
        ByteBuf uncompressed = Unpooled.buffer();
        VarInts.writeUnsignedInt(uncompressed, 10);
        uncompressed.writeByte(1);
        try {
            assertThrows(CorruptedFrameException.class,
                    () -> channel.writeInbound(BedrockBatchWrapper.newInstance(null, uncompressed)));
            assertNull(channel.readInbound());
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    private static BedrockBatchWrapper batch(int... lengths) {
        ByteBuf uncompressed = Unpooled.buffer();
        for (int length : lengths) {
            VarInts.writeUnsignedInt(uncompressed, length);
            uncompressed.writeZero(length);
        }
        return BedrockBatchWrapper.newInstance(null, uncompressed);
    }
}
