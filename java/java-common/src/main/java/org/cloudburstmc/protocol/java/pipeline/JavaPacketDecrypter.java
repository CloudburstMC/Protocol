package org.cloudburstmc.protocol.java.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import java.util.List;

// TODO: Native encryption
@RequiredArgsConstructor
public class JavaPacketDecrypter extends MessageToMessageDecoder<ByteBuf> {
    private final Cipher cipher;

    private byte[] heap = new byte[0];

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            int length = in.readableBytes();
            byte[] bytes = toByteArray(in);
            ByteBuf heapBuffer = ctx.alloc().heapBuffer(this.cipher.getOutputSize(length));
            heapBuffer.writerIndex(this.cipher.update(bytes, 0, length, heapBuffer.array(), heapBuffer.arrayOffset()));
            out.add(heapBuffer);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private byte[] toByteArray(ByteBuf buffer) {
        int length = buffer.readableBytes();
        if (this.heap.length < length) {
            this.heap = new byte[length];
        }
        buffer.readBytes(this.heap, 0, length);
        return this.heap;
    }
}
