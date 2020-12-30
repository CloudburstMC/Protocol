package org.cloudburstmc.protocol.java.network;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AllArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.JavaSession;

@AllArgsConstructor
public class JavaPacketEncoder extends MessageToByteEncoder<JavaPacket<?>> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(JavaPacketEncoder.class);

    private final JavaSession session;

    @Override
    protected void encode(ChannelHandlerContext ctx, JavaPacket<?> packet, ByteBuf out) throws Exception {
        int packetId = this.session.getPacketCodec().getId(packet);
        try {
            VarInts.writeUnsignedInt(out, packetId);
            this.session.getPacketCodec().tryEncode(out, packet, this.session);
        } catch (Throwable ex) {
            log.error("Error encoding packet: %s", packetId, ex);
        }
    }
}
