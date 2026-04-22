package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

@Sharable
public class BedrockBatchDecoder extends MessageToMessageDecoder<BedrockBatchWrapper> {

    public static final String NAME = "bedrock-batch-decoder";

    private static final int MAX_SUBPACKETS_PER_BATCH = 256;

    @Override
    protected void decode(ChannelHandlerContext ctx, BedrockBatchWrapper msg, List<Object> out) {
        if (msg.getUncompressed() == null) {
            throw new IllegalStateException("Batch packet was not decompressed");
        }

        ByteBuf buffer = msg.getUncompressed().slice();
        int count = 0;
        while (buffer.isReadable()) {
            // Bug 10 fix: cap sub-packet count to prevent decompression bomb amplification
            if (++count > MAX_SUBPACKETS_PER_BATCH) {
                throw new IllegalStateException("Batch contained " + count
                        + " sub-packets, exceeding maximum of " + MAX_SUBPACKETS_PER_BATCH);
            }
            int packetLength = VarInts.readUnsignedInt(buffer);
            ByteBuf packetBuf = buffer.readRetainedSlice(packetLength);
            out.add(packetBuf);
        }
    }
}
