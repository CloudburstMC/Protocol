package org.cloudburstmc.protocol.java.network;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class JavaVarInt21FrameDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) {
            in.skipBytes(in.readableBytes());
            return;
        }
        in.markReaderIndex();
        byte[] bytes = new byte[3];
        for (int i = 0; i < bytes.length; i++) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return;
            }
            bytes[i] = in.readByte();
            if (bytes[i] < 0) {
                continue;
            }
            int length = VarInts.readUnsignedInt(Unpooled.wrappedBuffer(bytes));
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }
            out.add(in.readBytes(length));
            return;
        }
        throw new CorruptedFrameException("VarInt length wider than 21-bit!");
    }
}
