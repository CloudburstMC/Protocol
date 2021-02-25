package org.cloudburstmc.protocol.java.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;

// TODO: Native encryption
@RequiredArgsConstructor
public class JavaPacketEncrypter extends MessageToByteEncoder<ByteBuf> {
    private final Cipher cipher;

    private byte[] heap = new byte[0];

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
        try {
            int length = in.readableBytes();
            byte[] bytes = toByteArray(in);
            int outputSize = this.cipher.getOutputSize(length);
            if (this.heap.length < outputSize) {
                this.heap = new byte[outputSize];
            }
            out.writeBytes(this.heap, 0, this.cipher.update(bytes, 0, length, this.heap));
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
