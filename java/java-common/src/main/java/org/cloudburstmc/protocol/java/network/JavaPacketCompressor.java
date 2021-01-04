package org.cloudburstmc.protocol.java.network;

import com.nukkitx.natives.util.Natives;
import com.nukkitx.natives.zlib.Deflater;
import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import org.cloudburstmc.protocol.java.JavaSession;

import java.nio.ByteBuffer;

@AllArgsConstructor
public class JavaPacketCompressor extends MessageToByteEncoder<ByteBuf> {
    private static final ThreadLocal<Deflater> DEFLATER = new ThreadLocal<Deflater>(){
        @Override
        protected Deflater initialValue() {
            return Natives.ZLIB.get().create(-1, false);
        }
    };

    private final JavaSession session;

    private final ByteBuffer buffer = ByteBuffer.allocate(8192);

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
        int length = in.readableBytes();
        if (length < this.session.getCompressionThreshold()) {
            VarInts.writeUnsignedInt(out, 0);
            out.writeBytes(in);
            return;
        }

        VarInts.writeUnsignedInt(out, in.readableBytes());
        Deflater deflater = DEFLATER.get();
        deflater.setInput(in.internalNioBuffer(in.readerIndex(), in.readableBytes()));
        while (!deflater.finished()) {
            int size = deflater.deflate(this.buffer);
            out.writeBytes(this.buffer.array(), 0, size);
        }
        deflater.reset();
    }
}
