package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.ReferenceCountUtil;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.packet.BedrockPacketCodec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BedrockBatchEncoderTest {

    /**
     * Payload sizes chosen to cover every VarInt length boundary the prefix can take.
     */
    private static final int[] LENGTHS = {0, 1, 5, 127, 128, 129, 16383, 16384, 16385, 100_000};

    /**
     * The reserved-prefix path and the separate-header fallback must put the same bytes on the wire.
     */
    @Test
    public void reservedPrefixMatchesSeparateHeader() {
        byte[] withReservation = encodeBatch(true);
        byte[] withoutReservation = encodeBatch(false);
        assertArrayEquals(withoutReservation, withReservation);
    }

    @Test
    public void reservedPrefixLeavesPacketBufferReadable() {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchEncoder());
        BedrockPacketWrapper wrapper = wrapper(payload(64), true);
        try {
            channel.writeOutbound(wrapper.retain());
            BedrockBatchWrapper batch = channel.readOutbound();
            assertNotNull(batch);
            try {
                // The batch keeps the packet for inspection, so the buffer it exposes must still
                // start at the packet itself rather than at the length prefix written ahead of it.
                ByteBuf buffer = batch.getPackets().get(0).getPacketBuffer();
                assertEquals(64, buffer.readableBytes());
                assertArrayEquals(payloadBytes(64), ByteBufUtil.getBytes(buffer));
            } finally {
                batch.release();
            }
        } finally {
            ReferenceCountUtil.safeRelease(wrapper);
            channel.finishAndReleaseAll();
        }
    }

    /**
     * A cached packet is encoded once and the same wrapper is broadcast to many channels, whose
     * batch encoders all see this buffer. Batching must leave the shared buffer's indices alone,
     * so every channel produces the same bytes no matter how the encoders interleave.
     */
    @Test
    public void sharedWrapperBatchesIdenticallyOnEveryChannel() {
        BedrockPacketWrapper wrapper = wrapper(payload(64), true);
        try {
            byte[] first = null;
            for (int i = 0; i < 2; i++) {
                EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchEncoder());
                channel.writeOutbound(wrapper.retain());
                BedrockBatchWrapper batch = channel.readOutbound();
                assertNotNull(batch);
                try {
                    byte[] bytes = ByteBufUtil.getBytes(batch.getUncompressed());
                    if (first == null) {
                        first = bytes;
                    } else {
                        assertArrayEquals(first, bytes);
                    }
                } finally {
                    batch.release();
                }
                channel.finishAndReleaseAll();
                // The buffer must still start at the packet, or the next channel prefixes garbage.
                assertEquals(BedrockPacketCodec.MAX_LENGTH_PREFIX_BYTES,
                        wrapper.getPacketBuffer().readerIndex());
            }
        } finally {
            wrapper.release();
        }
    }

    /**
     * A reservation is only usable while the buffer still starts where the encoder left it. If
     * something consumed from it, the prefix must not be written over packet bytes.
     */
    @Test
    public void consumedReservationFallsBackToSeparateHeader() {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchEncoder());
        try {
            ByteBuf payload = payload(16);
            payload.skipBytes(4); // as if a handler had read part of the packet
            BedrockPacketWrapper wrapper = wrapper(payload, true);
            channel.write(wrapper);
            channel.flushOutbound();

            BedrockBatchWrapper batch = channel.readOutbound();
            assertNotNull(batch);
            try {
                byte[] expected = new byte[13];
                expected[0] = 12; // VarInt length of the remaining payload
                System.arraycopy(payloadBytes(16), 4, expected, 1, 12);
                assertArrayEquals(expected, ByteBufUtil.getBytes(batch.getUncompressed()));
            } finally {
                batch.release();
            }
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    /**
     * Proxies swap a wrapper's buffer to force a re-encode. The reservation describes the old
     * buffer, so it must not survive into the new one.
     */
    @Test
    public void replacingThePacketBufferClearsTheReservation() {
        BedrockPacketWrapper wrapper = BedrockPacketWrapper.create();
        try {
            ByteBuf first = payload(8);
            wrapper.setPacketBuffer(first);
            wrapper.setReservedPrefixBytes(BedrockPacketCodec.MAX_LENGTH_PREFIX_BYTES);
            assertEquals(BedrockPacketCodec.MAX_LENGTH_PREFIX_BYTES, wrapper.getReservedPrefixBytes());

            wrapper.setPacketBuffer(null);
            assertEquals(0, wrapper.getReservedPrefixBytes());
            first.release();

            wrapper.setPacketBuffer(Unpooled.buffer(8));
            assertEquals(0, wrapper.getReservedPrefixBytes());
        } finally {
            wrapper.release();
        }
    }

    private static byte[] encodeBatch(boolean reserved) {
        EmbeddedChannel channel = new EmbeddedChannel(new BedrockBatchEncoder());
        try {
            for (int length : LENGTHS) {
                channel.write(wrapper(payload(length), reserved));
            }
            channel.flushOutbound();

            BedrockBatchWrapper batch = channel.readOutbound();
            assertNotNull(batch);
            try {
                assertEquals(LENGTHS.length, batch.getPackets().size());
                return ByteBufUtil.getBytes(batch.getUncompressed());
            } finally {
                batch.release();
            }
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    private static BedrockPacketWrapper wrapper(ByteBuf payload, boolean reserved) {
        BedrockPacketWrapper wrapper = BedrockPacketWrapper.create();
        wrapper.setPacketBuffer(payload);
        if (reserved) {
            wrapper.setReservedPrefixBytes(BedrockPacketCodec.MAX_LENGTH_PREFIX_BYTES);
        }
        return wrapper;
    }

    /**
     * Mirrors how the packet codec lays out an encode buffer: payload preceded by reserved room.
     */
    private static ByteBuf payload(int length) {
        int reserved = BedrockPacketCodec.MAX_LENGTH_PREFIX_BYTES;
        ByteBuf buf = Unpooled.buffer(reserved + length);
        buf.setIndex(reserved, reserved);
        buf.writeBytes(payloadBytes(length));
        return buf;
    }

    private static byte[] payloadBytes(int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (i % 251);
        }
        return bytes;
    }
}
