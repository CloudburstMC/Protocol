package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

@Sharable
public class BedrockBatchDecoder extends MessageToMessageDecoder<BedrockBatchWrapper> {

    public static final String NAME = "bedrock-batch-decoder";

    /**
     * Default limit for batches read from clients, set by {@code bedrock.maxPacketsPerBatch}.
     */
    public static final int DEFAULT_MAX_PACKETS = Integer.getInteger("bedrock.maxPacketsPerBatch", 1024);
    /**
     * Default limit for batches read from servers, set by {@code bedrock.maxPacketsPerServerBatch}. Servers batch far
     * more packets than clients.
     */
    public static final int DEFAULT_MAX_SERVER_PACKETS = Integer.getInteger("bedrock.maxPacketsPerServerBatch", 65536);

    private final int maxPackets;

    public BedrockBatchDecoder() {
        this(DEFAULT_MAX_PACKETS);
    }

    /**
     * @param maxPackets the most packets one batch may hold
     */
    public BedrockBatchDecoder(int maxPackets) {
        if (maxPackets < 1) {
            throw new IllegalArgumentException("maxPackets must be positive");
        }
        this.maxPackets = maxPackets;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BedrockBatchWrapper msg, List<Object> out) {
        if (msg.getUncompressed() == null) {
            throw new IllegalStateException("Batch packet was not decompressed");
        }

        ByteBuf buffer = msg.getUncompressed().slice();

        // Validated in full before anything is added to out
        int packets = 0;
        while (buffer.isReadable()) {
            int packetLength = VarInts.readUnsignedInt(buffer);
            if (packetLength <= 0 || packetLength > buffer.readableBytes()) {
                throw new CorruptedFrameException("Invalid packet length " + packetLength);
            }
            if (++packets > this.maxPackets) {
                throw new CorruptedFrameException("Batch holds more than " + this.maxPackets + " packets");
            }
            buffer.skipBytes(packetLength);
        }

        buffer.readerIndex(0);
        while (buffer.isReadable()) {
            int packetLength = VarInts.readUnsignedInt(buffer);
            out.add(buffer.readRetainedSlice(packetLength));
        }
    }
}
