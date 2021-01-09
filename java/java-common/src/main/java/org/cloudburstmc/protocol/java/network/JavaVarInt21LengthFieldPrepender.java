package org.cloudburstmc.protocol.java.network;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JavaVarInt21LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        VarInts.writeUnsignedInt(out, in.readableBytes());
        out.writeBytes(in);
    }
}