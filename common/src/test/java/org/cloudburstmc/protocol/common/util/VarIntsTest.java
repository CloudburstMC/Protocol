package org.cloudburstmc.protocol.common.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link VarInts#sizeOfUnsignedInt(int)} and {@link VarInts#setUnsignedInt(ByteBuf, int, int)} describe and
 * reproduce what {@link VarInts#writeUnsignedInt(ByteBuf, int)} does, but do not share its implementation.
 * These tests pin them to it, so the three cannot drift apart.
 */
public class VarIntsTest {

    /**
     * Every VarInt width boundary, every power of two and its neighbours, the signed extremes, a small
     * exhaustive range, and a deterministic random sample.
     */
    private static int[] values() {
        int count = 2000 + 4 * 5 + 32 * 3 + 4 + 20000;
        int[] values = new int[count];
        int i = 0;

        for (int value = 0; value < 2000; value++) {
            values[i++] = value;
        }
        for (int bits : new int[]{7, 14, 21, 28}) {
            int boundary = 1 << bits;
            for (int delta = -2; delta <= 2; delta++) {
                values[i++] = boundary + delta;
            }
        }
        for (int bit = 0; bit < 32; bit++) {
            int power = 1 << bit;
            values[i++] = power - 1;
            values[i++] = power;
            values[i++] = power + 1;
        }
        values[i++] = Integer.MAX_VALUE;
        values[i++] = Integer.MIN_VALUE;
        values[i++] = -1;
        values[i++] = -2;

        Random random = new Random(20260902L);
        while (i < count) {
            values[i++] = random.nextInt();
        }
        return values;
    }

    @Test
    public void sizeOfUnsignedIntMatchesWriteUnsignedInt() {
        ByteBuf buf = Unpooled.buffer(16);
        try {
            for (int value : values()) {
                buf.clear();
                VarInts.writeUnsignedInt(buf, value);
                assertEquals(buf.readableBytes(), VarInts.sizeOfUnsignedInt(value),
                        "size mismatch for " + value);
            }
        } finally {
            buf.release();
        }
    }

    @Test
    public void setUnsignedIntMatchesWriteUnsignedInt() {
        ByteBuf expected = Unpooled.buffer(16);
        ByteBuf actual = Unpooled.buffer(32);
        try {
            for (int value : values()) {
                expected.clear();
                VarInts.writeUnsignedInt(expected, value);

                actual.clear().writerIndex(32);
                VarInts.setUnsignedInt(actual, 4, value);

                byte[] written = new byte[VarInts.sizeOfUnsignedInt(value)];
                actual.getBytes(4, written);
                assertArrayEquals(ByteBufUtil.getBytes(expected), written, "bytes mismatch for " + value);
            }
        } finally {
            expected.release();
            actual.release();
        }
    }

    @Test
    public void setUnsignedIntRoundTripsThroughReadUnsignedInt() {
        ByteBuf buf = Unpooled.buffer(32);
        try {
            for (int value : values()) {
                buf.clear().writerIndex(32);
                VarInts.setUnsignedInt(buf, 4, value);

                buf.setIndex(4, 4 + VarInts.sizeOfUnsignedInt(value));
                assertEquals(value, VarInts.readUnsignedInt(buf), "round trip failed for " + value);
                assertEquals(0, buf.readableBytes(), "reader did not consume exactly the prefix for " + value);
            }
        } finally {
            buf.release();
        }
    }

    /**
     * Absolute writes must leave the indices alone, the way {@link ByteBuf#setByte(int, int)} does.
     */
    @Test
    public void setUnsignedIntLeavesIndicesUntouched() {
        ByteBuf buf = Unpooled.buffer(32);
        try {
            buf.writerIndex(32).readerIndex(9);
            VarInts.setUnsignedInt(buf, 2, Integer.MIN_VALUE);
            assertEquals(9, buf.readerIndex());
            assertEquals(32, buf.writerIndex());
        } finally {
            buf.release();
        }
    }

    /**
     * The batch encoder writes a prefix into a gap ahead of an already encoded packet, so a write must stay
     * inside the bytes {@link VarInts#sizeOfUnsignedInt(int)} accounts for and not disturb either neighbour.
     */
    @Test
    public void setUnsignedIntWritesOnlyTheBytesItAccountsFor() {
        int index = 6;
        int capacity = 32;
        ByteBuf buf = Unpooled.buffer(capacity);
        try {
            for (int value : values()) {
                buf.clear().writerIndex(capacity);
                for (int i = 0; i < capacity; i++) {
                    buf.setByte(i, 0xAA);
                }

                VarInts.setUnsignedInt(buf, index, value);

                int size = VarInts.sizeOfUnsignedInt(value);
                for (int i = 0; i < index; i++) {
                    assertEquals((byte) 0xAA, buf.getByte(i), "byte before the prefix changed for " + value);
                }
                for (int i = index + size; i < capacity; i++) {
                    assertEquals((byte) 0xAA, buf.getByte(i), "byte after the prefix changed for " + value);
                }
            }
        } finally {
            buf.release();
        }
    }
}
