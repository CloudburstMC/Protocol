package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.cloudburstmc.netty.channel.TransportChannel;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayDeque;
import java.util.Queue;

public class BedrockBatchEncoder extends ChannelOutboundHandlerAdapter {

    public static final String NAME = "bedrock-batch-encoder";

    private final Queue<BedrockPacketWrapper> messages = new ArrayDeque<>();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof BedrockPacketWrapper)) {
            super.write(ctx, msg, promise);
            return;
        }

        // Accumulate messages to batch
        this.messages.add((BedrockPacketWrapper) msg);
        promise.trySuccess(); // complete write promise here
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (!messages.isEmpty()) {
            int maxBatchSize = maxBatchSize(ctx);
            while (!messages.isEmpty()) {
                this.writeBatch(ctx, maxBatchSize);
            }
        }
        super.flush(ctx);
    }

    /**
     * Uncompressed bytes a batch may hold before the remaining packets go out in another, or {@link Integer#MAX_VALUE}
     * for no limit. A batch travels as one message, which may be no larger than the transport allows, so this leaves
     * a little room for compression and encryption to grow it. A larger single packet still goes alone.
     */
    private static int maxBatchSize(ChannelHandlerContext ctx) {
        if (!(ctx.channel() instanceof TransportChannel)) {
            return Integer.MAX_VALUE;
        }
        int maxMessageSize = ((TransportChannel) ctx.channel()).maxMessageSize();
        return maxMessageSize - maxMessageSize / 64;
    }

    /**
     * Writes queued packets as one batch, leaving the rest queued once it would grow past {@code maxBatchSize}.
     */
    private void writeBatch(ChannelHandlerContext ctx, int maxBatchSize) {
        CompositeByteBuf buf = ctx.alloc().compositeDirectBuffer(messages.size() * 2);
        BedrockBatchWrapper batch = BedrockBatchWrapper.newInstance();

        try {
            BedrockPacketWrapper packet;
            while ((packet = messages.peek()) != null) {
                ByteBuf message = packet.getPacketBuffer();
                if (message != null && buf.isReadable()) {
                    int length = message.readableBytes();
                    if (buf.readableBytes() + VarInts.sizeOfUnsignedInt(length) + length > maxBatchSize) {
                        break;
                    }
                }
                messages.poll();
                try {
                    if (message == null) {
                        throw new IllegalArgumentException("BedrockPacket is not encoded");
                    }

                    addPrefixed(ctx, buf, message, packet.getReservedPrefixBytes());
                    batch.addPacket(packet.retain());
                } finally {
                    packet.release();
                }
            }

            batch.setUncompressed(buf.retain());
            ctx.write(batch.retain());
        } finally {
            buf.release();
            batch.release();
        }
    }

    /**
     * Appends {@code message} to {@code buf} behind its VarInt length.
     *
     * <p>When the encoder that produced the message reserved room ahead of it, the prefix is
     * written into that gap and the whole thing goes in as a single component. Otherwise the
     * prefix needs a buffer of its own, costing an allocation and a second component.
     */
    public static void addPrefixed(ChannelHandlerContext ctx, CompositeByteBuf buf, ByteBuf message,
                                    int reservedPrefixBytes) {
        int length = message.readableBytes();
        int prefixSize = VarInts.sizeOfUnsignedInt(length);
        // The gap is only intact while the buffer still starts where the encoder left it.
        if (reservedPrefixBytes >= prefixSize && message.readerIndex() == reservedPrefixBytes) {
            int prefixIndex = reservedPrefixBytes - prefixSize;
            VarInts.setUnsignedInt(message, prefixIndex, length);
            // Absolute-range slice: a cached packet's wrapper is broadcast to many channels, so
            // this buffer's indices must never move, even briefly.
            buf.addComponent(true, message.retainedSlice(prefixIndex, prefixSize + length));
            return;
        }

        ByteBuf header = ctx.alloc().ioBuffer(prefixSize);
        VarInts.writeUnsignedInt(header, length);
        buf.addComponent(true, header);
        buf.addComponent(true, message.retain());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        BedrockPacketWrapper message;
        while ((message = messages.poll()) != null) {
            message.release();
        }
        super.handlerRemoved(ctx);
    }
}
