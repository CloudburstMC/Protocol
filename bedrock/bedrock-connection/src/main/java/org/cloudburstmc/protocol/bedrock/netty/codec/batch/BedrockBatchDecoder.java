package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class BedrockBatchDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        while (in.isReadable()) {
            int packetLength = VarInts.readUnsignedInt(in);
            ByteBuf packetBuf = in.readRetainedSlice(packetLength);

            out.add(packetBuf);
        }
    }
}
