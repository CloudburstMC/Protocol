package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class UnknownPacket extends BedrockPacket {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(UnknownPacket.class);
    private short id;
    private ByteBuf payload;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeShort(id);
        buffer.writeBytes(payload);
    }

    @Override
    public void decode(ByteBuf buffer) {
        id = buffer.readUnsignedByte();
        payload = buffer.readBytes(buffer.readableBytes());
    }

    @Override
    public String toString() {
        return "UNKNOWN - " + Integer.toHexString(id) + " - Hex: " + ByteBufUtil.hexDump(payload);
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return false;
    }
}
