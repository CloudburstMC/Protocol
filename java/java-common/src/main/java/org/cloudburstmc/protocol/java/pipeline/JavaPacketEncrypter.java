package org.cloudburstmc.protocol.java.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import java.util.List;

@RequiredArgsConstructor
public class JavaPacketEncrypter extends MessageToMessageEncoder<ByteBuf> {
    private final Cipher cipher;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO: Native encryption?
        // Encryption is copy safe so we can use the same buffer.
        this.cipher.update(in.internalNioBuffer(in.readerIndex(), in.readableBytes()),
                in.internalNioBuffer(in.readerIndex(), in.readableBytes()));

        out.add(in);
    }
}
