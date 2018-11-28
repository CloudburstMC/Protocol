package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class UnknownPacket extends BedrockPacket implements PacketSerializer<UnknownPacket> {
    private ByteBuf payload;

    @Override
    public void serialize(ByteBuf buffer, UnknownPacket packet) {
        buffer.writeBytes(packet.payload);
    }

    @Override
    public void deserialize(ByteBuf buffer, UnknownPacket packet) {
        packet.payload = buffer.readBytes(buffer.readableBytes());
    }

    @Override
    public String toString() {
        return "UNKNOWN - " + getHeader() + " - Hex: " + ByteBufUtil.hexDump(payload);
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return false;
    }
}
