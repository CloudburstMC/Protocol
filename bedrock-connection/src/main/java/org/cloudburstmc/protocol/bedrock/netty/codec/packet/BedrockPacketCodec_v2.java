package org.cloudburstmc.protocol.bedrock.netty.codec.packet;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;

public class BedrockPacketCodec_v2 extends BedrockPacketCodec {

    @Override
    public void encodeHeader(ByteBuf buf, BedrockPacketWrapper msg) {
        buf.writeByte(msg.getPacketId());
        buf.writeByte(msg.getSenderSubClientId());
        buf.writeByte(msg.getTargetSubClientId());
    }

    @Override
    public void decodeHeader(ByteBuf buf, BedrockPacketWrapper msg) {
        msg.setPacketId(buf.readUnsignedByte());
        msg.setSenderSubClientId(buf.readUnsignedByte());
        msg.setTargetSubClientId(buf.readUnsignedByte());
    }
}
