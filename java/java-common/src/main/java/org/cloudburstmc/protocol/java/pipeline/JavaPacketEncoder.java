package org.cloudburstmc.protocol.java.pipeline;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AllArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.JavaPacketCodec;
import org.cloudburstmc.protocol.java.JavaSession;

@AllArgsConstructor
public class JavaPacketEncoder extends MessageToByteEncoder<JavaPacket<?>> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(JavaPacketEncoder.class);

    private final JavaSession session;

    @Override
    protected void encode(ChannelHandlerContext ctx, JavaPacket<?> packet, ByteBuf out) throws Exception {
        JavaPacketCodec.JavaStateCodec stateCodec = this.session.getPacketCodec().getCodec(packet.getPacketType().getState());
        int packetId = stateCodec.getId(packet);
        try {
            VarInts.writeUnsignedInt(out, packetId);
            stateCodec.tryEncode(out, packet, this.session);
        } catch (Throwable ex) {
            log.error("Error encoding packet: {}", packetId, ex);
        }
    }
}
