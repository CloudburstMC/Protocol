package org.cloudburstmc.protocol.bedrock.netty.codec.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.cloudburstmc.protocol.bedrock.PacketDirection;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.compat.BedrockCompat;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.UnknownPacket;

import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class BedrockPacketCodec extends MessageToMessageCodec<ByteBuf, BedrockPacketWrapper> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPacketCodec.class);
    public static final String NAME = "bedrock-packet-codec";

    /**
     * Bytes reserved ahead of an encoded packet so the batch encoder can write its length prefix
     * in place instead of prepending a separate buffer. Matches the largest unsigned VarInt.
     */
    public static final int MAX_LENGTH_PREFIX_BYTES = 5;

    /**
     * Initial encode buffer size, reservation included. Sits exactly on a pooled size class so the
     * reservation is free: asking for 128 plus the prefix rounds up to the next class (160) and grows
     * every encode buffer by a quarter to buy five bytes.
     */
    private static final int INITIAL_BUFFER_SIZE = 128;

    private BedrockCodec codec = BedrockCompat.CODEC;
    private BedrockCodecHelper helper = codec.createHelper();

    private PacketRecipient inboundRecipient;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        PacketDirection attribute = ctx.channel().attr(PacketDirection.ATTRIBUTE).get();
        if (attribute != null) {
            this.inboundRecipient = attribute.getInbound();
        }
    }

    @Override
    protected final void encode(ChannelHandlerContext ctx, BedrockPacketWrapper msg, List<Object> out) throws Exception {
        if (msg.getPacketBuffer() != null) {
            // We have a pre-encoded packet buffer, just use that.
            out.add(msg.retain());
        } else {
            ByteBuf buf = ctx.alloc().buffer(INITIAL_BUFFER_SIZE);
            try {
                // Leave room for the batch length prefix ahead of the packet, so batching can write
                // it in place. The reserved bytes sit before the reader index and are never read.
                buf.setIndex(MAX_LENGTH_PREFIX_BYTES, MAX_LENGTH_PREFIX_BYTES);

                BedrockPacket packet = msg.getPacket();
                msg.setPacketId(getPacketId(packet));
                encodeHeader(buf, msg);
                this.codec.tryEncode(helper, buf, packet);

                msg.setPacketBuffer(buf.retain());
                msg.setReservedPrefixBytes(MAX_LENGTH_PREFIX_BYTES);
                out.add(msg.retain());
            } catch (Throwable t) {
                if (log.isDebugEnabled()) {
                    log.debug("Error encoding packet {}", msg.getPacket(), t);
                }
                throw t;
            } finally {
                buf.release();
            }
        }
    }

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        BedrockPacketWrapper wrapper = BedrockPacketWrapper.create();
        wrapper.setPacketBuffer(msg.retainedSlice());
        try {
            int index = msg.readerIndex();
            this.decodeHeader(msg, wrapper);
            wrapper.setHeaderLength(msg.readerIndex() - index);
            wrapper.setPacket(this.codec.tryDecode(helper, msg, wrapper.getPacketId(), this.inboundRecipient));
            out.add(wrapper.retain());
        } catch (Throwable t) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to decode packet", t);
            }
            throw t;
        } finally {
            wrapper.release();
        }
    }

    public abstract void encodeHeader(ByteBuf buf, BedrockPacketWrapper msg);

    public abstract void decodeHeader(ByteBuf buf, BedrockPacketWrapper msg);

    public final int getPacketId(BedrockPacket packet) {
        if (packet instanceof UnknownPacket) {
            return ((UnknownPacket) packet).getPacketId();
        }
        return this.codec.getPacketDefinition(packet.getClass()).getId();
    }

    public final void setCodec(BedrockCodec codec) {
        this.codec = requireNonNull(codec, "Codec cannot be null");
        this.helper = codec.createHelper();
    }

    public final BedrockCodec getCodec() {
        return codec;
    }

    public BedrockCodecHelper getHelper() {
        return helper;
    }
}
