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

    @Override
    protected void decode(ChannelHandlerContext ctx, BedrockBatchWrapper msg, List<Object> out) {
        if (msg.getUncompressed() == null) {
            throw new IllegalStateException("Batch packet was not decompressed");
        }

        ByteBuf buffer = msg.getUncompressed().slice();
        while (buffer.isReadable()) {
            int packetLength = VarInts.readUnsignedInt(buffer);
            ByteBuf packetBuf = buffer.readRetainedSlice(packetLength);
            out.add(packetBuf);
        }
    }
}
