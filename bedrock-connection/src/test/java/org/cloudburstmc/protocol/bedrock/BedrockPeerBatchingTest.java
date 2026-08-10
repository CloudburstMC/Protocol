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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    public void sendPacketDuringClosingIsReleasedNotQueued() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);

        testPeer.peer.close("bye");

        testPeer.peer.sendPacket(0, 0, new PlayStatusPacket());

        assertTrue(testPeer.peer.closing.get());
        assertTrue(testPeer.peer.packetQueue.isEmpty());
        advanceToFlush(testPeer.channel);
        assertNull(testPeer.channel.readOutbound());
        testPeer.peer.onClose();
        testPeer.channel.finishAndReleaseAll();
    }

    @Test
    public void closingStopsFlushingAndOnCloseFreesTheQueue() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);
        testPeer.peer.sendPacket(0, 0, new PlayStatusPacket());
        BedrockPacketWrapper queuedWrapper = testPeer.peer.packetQueue.peek();

        testPeer.peer.close("bye");

        // A flush arriving after the close request must not write; the queue is
        // freed by onClose(), never delivered.
        testPeer.peer.flushPacketQueue();
        assertNull(testPeer.channel.readOutbound());
        assertEquals(0, counter.flushes);
        assertFalse(testPeer.peer.packetQueue.isEmpty());

        testPeer.peer.onClose();
        assertTrue(testPeer.peer.packetQueue.isEmpty());
        assertEquals(0, queuedWrapper.refCnt());
        testPeer.channel.finishAndReleaseAll();
    }

    @Test
    public void packetEnqueuedAfterCloseIsReleased() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);

        testPeer.channel.close();
        testPeer.peer.onClose();

        // Simulates a producer that passed the sendPacket() door gate just before
        // closing was set: the wrapper is enqueued with no flush task left.
        testPeer.peer.packetQueue.add(BedrockPacketWrapper.create(0, 0, 0, new PlayStatusPacket(), null));
        BedrockPacketWrapper queuedWrapper = testPeer.peer.packetQueue.peek();
        assertNotNull(queuedWrapper);
        testPeer.peer.schedulePacketFlush();
        testPeer.channel.runPendingTasks();

        assertTrue(testPeer.peer.packetQueue.isEmpty());
        assertEquals(0, queuedWrapper.refCnt());
        assertNull(testPeer.channel.readOutbound());
        assertEquals(0, counter.flushes);
        testPeer.channel.finishAndReleaseAll();
    }

    @Test
    public void closeTearsDownAllSessionsDespiteSelfRemoval() {
        FlushCounter counter = new FlushCounter();
        TestPeer testPeer = createPeer(counter);
        // Sessions remove themselves from the open addressing map during
        // onClose(); these ids previously corrupted the iteration mid teardown.
        for (int id : new int[]{0, 1, 33, 66}) {
            testPeer.peer.sessions.put(id, new BedrockSession(testPeer.peer, id) {
                @Override
                public void disconnect(CharSequence reason, boolean hideReason) {
                }
            });
        }
        testPeer.peer.sendPacket(0, 0, new PlayStatusPacket());
        BedrockPacketWrapper queuedWrapper = testPeer.peer.packetQueue.peek();

        testPeer.channel.close();
        testPeer.peer.onClose();

        assertTrue(testPeer.peer.sessions.isEmpty());
        assertTrue(testPeer.peer.packetQueue.isEmpty());
        assertEquals(0, queuedWrapper.refCnt());
        testPeer.channel.finishAndReleaseAll();
    }

    @Test
    public void rejectedFlushScheduleRetriesUntilThePacketFlushes() {
        FlushCounter counter = new FlushCounter();
        EmbeddedChannel channel = new EmbeddedChannel(counter);
        channel.freezeTime();
        RejectingPeer peer = new RejectingPeer(channel);
        PlayStatusPacket packet = new PlayStatusPacket();

        peer.rejectFlushSchedules = true;
        peer.sendPacket(0, 0, packet);

        assertFalse(peer.flushScheduled.get());
        assertFalse(peer.packetQueue.isEmpty());
        assertEquals(1, peer.retries.size());

        // The retry re-enters the normal scheduling path once the loop accepts.
        peer.rejectFlushSchedules = false;
        peer.retries.remove(0).run();

        channel.advanceTimeBy(BedrockPeer.BATCH_FLUSH_DELAY_NANOS, TimeUnit.NANOSECONDS);
        channel.runScheduledPendingTasks();

        assertOutboundPacket(channel, packet);
        assertNull(channel.readOutbound());
        assertTrue(peer.packetQueue.isEmpty());
        assertFalse(peer.flushScheduled.get());
        channel.close();
        peer.onClose();
        channel.finishAndReleaseAll();
    }

    @Test
    public void rejectedOffLoopCloseRetriesUntilTheCloseLands() {
        FlushCounter counter = new FlushCounter();
        EmbeddedChannel channel = new EmbeddedChannel(counter);
        channel.freezeTime();
        RejectingPeer peer = new RejectingPeer(channel);
        BedrockSession session = new BedrockSession(peer, 0) {
            @Override
            public void disconnect(CharSequence reason, boolean hideReason) {
            }
        };
        peer.sessions.put(0, session);

        peer.rejectNextExecute = true;
        peer.simulateOffLoop = true;
        peer.close("Bye");

        assertTrue(channel.isOpen());
        assertNotEquals("Bye", session.disconnectReason);
        assertEquals(1, peer.retries.size());

        // In production the retry runs on the global executor, still off the
        // loop, so it must submit close() to the event loop again.
        peer.retries.remove(0).run();
        assertNotEquals("Bye", session.disconnectReason);

        peer.simulateOffLoop = false;
        channel.runPendingTasks();

        assertEquals("Bye", session.disconnectReason);
        assertFalse(channel.isOpen());
        peer.onClose();
        channel.finishAndReleaseAll();
    }

    @Test
    public void persistentRejectionKeepsExactlyOneRetryChain() {
        FlushCounter counter = new FlushCounter();
        EmbeddedChannel channel = new EmbeddedChannel(counter);
        channel.freezeTime();
        RejectingPeer peer = new RejectingPeer(channel);
        peer.rejectFlushSchedules = true;

        peer.sendPacket(0, 0, new PlayStatusPacket());
        peer.sendPacket(0, 0, new PlayStatusPacket());
        peer.sendPacket(0, 0, new PlayStatusPacket());
        assertEquals(1, peer.retries.size());

        // A retry that is itself rejected continues the chain without forking.
        peer.retries.remove(0).run();
        assertEquals(1, peer.retries.size());

        peer.sendPacket(0, 0, new PlayStatusPacket());
        assertEquals(1, peer.retries.size());

        channel.close();
        peer.onClose();
        assertTrue(peer.packetQueue.isEmpty());
        channel.finishAndReleaseAll();
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

    private static final class RejectingPeer extends BedrockPeer {
        final java.util.List<Runnable> retries = new java.util.ArrayList<>();
        boolean rejectFlushSchedules;
        boolean rejectNextExecute;
        // EmbeddedEventLoop reports every thread as the loop thread, so the off
        // loop branch is simulated instead of using a real second thread.
        boolean simulateOffLoop;

        RejectingPeer(EmbeddedChannel channel) {
            super(channel, (peer, sessionId) -> {
                throw new AssertionError("A session should not be created in this test");
            });
        }

        @Override
        protected void scheduleFlushTask() {
            if (this.rejectFlushSchedules) {
                throw new java.util.concurrent.RejectedExecutionException("rejected by test");
            }
            super.scheduleFlushTask();
        }

        @Override
        protected void executeOnEventLoop(Runnable task) {
            if (this.rejectNextExecute) {
                this.rejectNextExecute = false;
                throw new java.util.concurrent.RejectedExecutionException("rejected by test");
            }
            super.executeOnEventLoop(task);
        }

        @Override
        protected boolean isOnEventLoop() {
            return !this.simulateOffLoop && super.isOnEventLoop();
        }

        @Override
        protected void scheduleRejectedRetry(Runnable retry) {
            this.retries.add(retry);
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
