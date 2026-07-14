package org.cloudburstmc.protocol.bedrock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.ReferenceCountUtil;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BedrockPeerBatchingTest {

    @Test
    public void normalPacketsAreCoalescedForOneMillisecond() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);
        PlayStatusPacket firstPacket = new PlayStatusPacket();
        PlayStatusPacket secondPacket = new PlayStatusPacket();

        try {
            testPeer.peer.sendPacket(0, 0, firstPacket);
            testPeer.peer.sendPacket(0, 0, secondPacket);

            testPeer.channel.advanceTimeBy(BedrockPeer.BATCH_FLUSH_DELAY_NANOS - 1, TimeUnit.NANOSECONDS);
            testPeer.channel.runScheduledPendingTasks();
            assertNull(testPeer.channel.readOutbound());
            assertEquals(0, counter.flushes);

            testPeer.channel.advanceTimeBy(1, TimeUnit.NANOSECONDS);
            testPeer.channel.runScheduledPendingTasks();

            assertOutboundPacket(testPeer.channel, firstPacket);
            assertOutboundPacket(testPeer.channel, secondPacket);
            assertNull(testPeer.channel.readOutbound());
            assertEquals(1, counter.flushes);
            assertTrue(testPeer.peer.packetQueue.isEmpty());
            assertFalse(testPeer.peer.flushScheduled.get());
        } finally {
            close(testPeer);
        }
    }

    @Test
    public void immediatePacketBypassesPendingBatch() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);
        PlayStatusPacket queuedPacket = new PlayStatusPacket();
        PlayStatusPacket immediatePacket = new PlayStatusPacket();

        try {
            testPeer.peer.sendPacket(0, 0, queuedPacket);
            testPeer.peer.sendPacketImmediately(0, 0, immediatePacket);

            assertOutboundPacket(testPeer.channel, immediatePacket);
            assertNull(testPeer.channel.readOutbound());
            assertEquals(1, counter.flushes);

            advanceToFlush(testPeer.channel);

            assertOutboundPacket(testPeer.channel, queuedPacket);
            assertNull(testPeer.channel.readOutbound());
            assertEquals(2, counter.flushes);
        } finally {
            close(testPeer);
        }
    }

    @Test
    public void packetEnqueuedDuringFlushSchedulesAnotherBatch() {
        EnqueueOnFirstFlush handler = new EnqueueOnFirstFlush();
        TestPeer testPeer = createPeer(handler);
        PlayStatusPacket firstPacket = new PlayStatusPacket();
        PlayStatusPacket secondPacket = new PlayStatusPacket();
        handler.peer = testPeer.peer;
        handler.packet = secondPacket;

        try {
            testPeer.peer.sendPacket(0, 0, firstPacket);
            advanceToFlush(testPeer.channel);

            assertOutboundPacket(testPeer.channel, firstPacket);
            assertNull(testPeer.channel.readOutbound());
            assertEquals(1, handler.flushes);
            assertTrue(testPeer.peer.flushScheduled.get());

            advanceToFlush(testPeer.channel);

            assertOutboundPacket(testPeer.channel, secondPacket);
            assertNull(testPeer.channel.readOutbound());
            assertEquals(2, handler.flushes);
            assertFalse(testPeer.peer.flushScheduled.get());
        } finally {
            close(testPeer);
        }
    }

    @Test
    public void closeReleasesQueuedPacketsAndPreventsScheduledWrite() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);
        testPeer.peer.sendPacket(0, 0, new PlayStatusPacket());
        BedrockPacketWrapper queuedWrapper = testPeer.peer.packetQueue.peek();

        testPeer.channel.close();
        testPeer.peer.onClose();

        assertTrue(testPeer.peer.packetQueue.isEmpty());
        assertEquals(0, queuedWrapper.refCnt());

        advanceToFlush(testPeer.channel);

        assertNull(testPeer.channel.readOutbound());
        assertEquals(0, counter.flushes);
        // flushScheduled is not asserted here: after close it is meaningless. On a
        // live loop the pending task clears it in its closed branch, but
        // EmbeddedChannel.close() cancels scheduled tasks so it stays set. Nothing
        // reads it post close since the closed check precedes the CAS.
        testPeer.channel.finishAndReleaseAll();
    }

    private static TestPeer createPeer(FlushCounter counter) {
        EmbeddedChannel channel = new EmbeddedChannel(counter);
        channel.freezeTime();
        BedrockPeer peer = new BedrockPeer(channel, (ignoredPeer, ignoredSessionId) -> {
            throw new AssertionError("A session should not be created in this test");
        });
        return new TestPeer(channel, peer);
    }

    private static void advanceToFlush(EmbeddedChannel channel) {
        channel.advanceTimeBy(BedrockPeer.BATCH_FLUSH_DELAY_NANOS, TimeUnit.NANOSECONDS);
        channel.runScheduledPendingTasks();
    }

    private static void assertOutboundPacket(EmbeddedChannel channel, BedrockPacket expectedPacket) {
        BedrockPacketWrapper wrapper = channel.readOutbound();
        assertNotNull(wrapper);
        try {
            assertSame(expectedPacket, wrapper.getPacket());
        } finally {
            ReferenceCountUtil.release(wrapper);
        }
    }

    private static void close(TestPeer testPeer) {
        testPeer.channel.close();
        testPeer.peer.onClose();
        testPeer.channel.finishAndReleaseAll();
    }

    private static class FlushCounter extends ChannelOutboundHandlerAdapter {
        int flushes;

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            this.flushes++;
            ctx.flush();
        }
    }

    private static final class EnqueueOnFirstFlush extends FlushCounter {
        private BedrockPeer peer;
        private BedrockPacket packet;
        private boolean enqueued;

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            if (!this.enqueued) {
                this.enqueued = true;
                this.peer.sendPacket(0, 0, this.packet);
            }
            super.flush(ctx);
        }
    }

    private static final class TestPeer {
        private final EmbeddedChannel channel;
        private final BedrockPeer peer;

        private TestPeer(EmbeddedChannel channel, BedrockPeer peer) {
            this.channel = channel;
            this.peer = peer;
        }
    }
}
