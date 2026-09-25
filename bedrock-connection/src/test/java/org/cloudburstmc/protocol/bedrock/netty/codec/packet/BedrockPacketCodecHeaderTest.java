package org.cloudburstmc.protocol.bedrock.netty.codec.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BedrockPacketCodecHeaderTest {

    /**
     * Split screen tops out at four players, and v3 packs each id into two bits of the header.
     */
    private static final int MAX_SUB_CLIENT_ID = 3;

    /**
     * v3 packs the packet id into ten bits; v1 and v2 write it as a single byte.
     */
    private static final int MAX_V3_PACKET_ID = 0x3ff;
    private static final int MAX_BYTE_PACKET_ID = 0xff;

    @Test
    public void v1RoundTripsPacketId() {
        BedrockPacketCodec codec = new BedrockPacketCodec_v1();
        for (int packetId = 0; packetId <= MAX_BYTE_PACKET_ID; packetId++) {
            assertRoundTrip(codec, packetId, 0, 0);
        }
    }

    @Test
    public void v2RoundTripsHeader() {
        assertRoundTripsEveryHeader(new BedrockPacketCodec_v2(), MAX_BYTE_PACKET_ID);
    }

    @Test
    public void v3RoundTripsHeader() {
        assertRoundTripsEveryHeader(new BedrockPacketCodec_v3(), MAX_V3_PACKET_ID);
    }

    /**
     * decode() measures the header width and narrows it to a byte. VarInts gives up after five
     * bytes, so no codec can produce a width anywhere near where that narrowing would lose data.
     */
    @Test
    public void headerWidthSurvivesByteNarrowing() {
        assertHeaderWidth(new BedrockPacketCodec_v1(), buffer(42), 1);
        assertHeaderWidth(new BedrockPacketCodec_v2(), buffer(42, 3, 2), 3);
        // Widest header the varint decoder accepts: four continuation bytes then a terminator.
        assertHeaderWidth(new BedrockPacketCodec_v3(), buffer(0xFF, 0xFF, 0xFF, 0xFF, 0x0F), 5);
    }

    /**
     * A sixth continuation byte trips VarInts before decodeHeader returns, so the width is never
     * measured and the wrapper is left untouched.
     */
    @Test
    public void overlongVarIntHeaderIsRejected() {
        BedrockPacketCodec codec = new BedrockPacketCodec_v3();
        ByteBuf buf = buffer(0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x0F);
        BedrockPacketWrapper wrapper = BedrockPacketWrapper.create();
        try {
            assertThrows(ArithmeticException.class, () -> codec.decodeHeader(buf, wrapper));
        } finally {
            wrapper.release();
            buf.release();
        }
    }

    private static void assertRoundTripsEveryHeader(BedrockPacketCodec codec, int maxPacketId) {
        for (int packetId = 0; packetId <= maxPacketId; packetId++) {
            for (int sender = 0; sender <= MAX_SUB_CLIENT_ID; sender++) {
                for (int target = 0; target <= MAX_SUB_CLIENT_ID; target++) {
                    assertRoundTrip(codec, packetId, sender, target);
                }
            }
        }
    }

    private static void assertRoundTrip(BedrockPacketCodec codec, int packetId, int sender, int target) {
        ByteBuf buf = Unpooled.buffer();
        BedrockPacketWrapper encoded = BedrockPacketWrapper.create(packetId, sender, target, null, null);
        BedrockPacketWrapper decoded = BedrockPacketWrapper.create();
        try {
            codec.encodeHeader(buf, encoded);
            int index = buf.readerIndex();
            codec.decodeHeader(buf, decoded);
            int width = buf.readerIndex() - index;

            assertEquals(packetId, decoded.getPacketId(), () -> describe(packetId, sender, target, "packet id"));
            assertEquals(sender, (int) decoded.getSenderSubClientId(), () -> describe(packetId, sender, target, "sender"));
            assertEquals(target, (int) decoded.getTargetSubClientId(), () -> describe(packetId, sender, target, "target"));

            decoded.setHeaderLength((byte) width);
            assertEquals(width, (int) decoded.getHeaderLength(), () -> describe(packetId, sender, target, "header width"));
            assertEquals(buf.writerIndex(), buf.readerIndex(), () -> describe(packetId, sender, target, "bytes consumed"));
        } finally {
            encoded.release();
            decoded.release();
            buf.release();
        }
    }

    private static void assertHeaderWidth(BedrockPacketCodec codec, ByteBuf buf, int expected) {
        BedrockPacketWrapper wrapper = BedrockPacketWrapper.create();
        try {
            int index = buf.readerIndex();
            codec.decodeHeader(buf, wrapper);
            int width = buf.readerIndex() - index;
            assertEquals(expected, width);
            wrapper.setHeaderLength((byte) width);
            assertEquals(width, (int) wrapper.getHeaderLength());
        } finally {
            wrapper.release();
            buf.release();
        }
    }

    private static String describe(int packetId, int sender, int target, String what) {
        return what + " changed for id=" + packetId + " sender=" + sender + " target=" + target;
    }

    private static ByteBuf buffer(int... bytes) {
        ByteBuf buf = Unpooled.buffer(bytes.length);
        for (int b : bytes) {
            buf.writeByte(b);
        }
        return buf;
    }
}
