package org.cloudburstmc.protocol.bedrock.netty.codec.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.compat.BedrockCompat;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

import java.util.List;

public abstract class BedrockPacketCodec extends MessageToMessageCodec<ByteBuf, BedrockPacketWrapper> {

    public static final String NAME = "bedrock-packet-codec";

    private BedrockCodec codec;
    private BedrockCodecHelper helper;

    @Override
    protected final void encode(ChannelHandlerContext ctx, BedrockPacketWrapper msg, List<Object> out) throws Exception {
        if (msg.getPacketBuffer() != null) {
            // We have a pre-encoded packet buffer, just use that.
            out.add(msg.retain());
        } else {
            ByteBuf buf = ctx.alloc().buffer(128);
            try {
                BedrockPacket packet = msg.getPacket();
                msg.setPacketId(getPacketId(packet));
                encodeHeader(buf, msg);
                this.codec.tryEncode(helper, buf, packet);
                out.add(buf.retain());
            } finally {
                buf.release();
            }
        }
    }

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        BedrockPacketWrapper wrapper = new BedrockPacketWrapper();
        wrapper.setPacketBuffer(msg.slice());
        try {
            decodeHeader(msg, wrapper);
            wrapper.setPacket(this.codec.tryDecode(helper, msg, wrapper.getPacketId()));
            out.add(wrapper.retain());
        } finally {
            wrapper.release();
        }
    }

    public abstract void encodeHeader(ByteBuf buf, BedrockPacketWrapper msg);

    public abstract void decodeHeader(ByteBuf buf, BedrockPacketWrapper msg);

    public final int getPacketId(BedrockPacket packet) {
        return this.codec.getPacketDefinition(packet.getClass()).getId();
    }

    public final void setCodec(BedrockCodec codec) {
        if (this.codec != BedrockCompat.COMPAT_CODEC) {
            throw new IllegalStateException("Codec is already set");
        }
        this.codec = codec;
        this.helper = codec.createHelper();
    }

    public final BedrockCodec getCodec() {
        return codec;
    }

    public BedrockCodecHelper getHelper() {
        return helper;
    }
}
