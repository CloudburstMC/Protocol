package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public final class UnknownPacket extends BedrockPacket implements BedrockPacketSerializer<UnknownPacket>, ReferenceCounted {
    private ByteBuf payload;

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UnknownPacket packet) {
        buffer.writeBytes(packet.payload);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UnknownPacket packet) {
        packet.payload = buffer.readRetainedSlice(buffer.readableBytes());
    }

    @Override
    public String toString() {
        return "UNKNOWN - " + getPacketId() + " - Hex: " + (payload == null || payload.refCnt() == 0 ? "null" : ByteBufUtil.hexDump(payload));
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return false;
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UNKNOWN;
    }

    @Override
    public int refCnt() {
        if (this.payload == null) {
            return 0;
        }
        return payload.refCnt();
    }

    @Override
    public UnknownPacket retain() {
        if (this.payload != null) {
            this.payload.retain();
        }
        return this;
    }

    @Override
    public UnknownPacket retain(int increment) {
        if (this.payload != null) {
            this.payload.retain(increment);
        }
        return this;
    }

    @Override
    public UnknownPacket touch() {
        if (this.payload != null) {
            this.payload.touch();
        }
        return this;
    }

    @Override
    public UnknownPacket touch(Object hint) {
        if (this.payload != null) {
            this.payload.touch(hint);
        }
        return this;
    }

    @Override
    public boolean release() {
        return this.payload == null || this.payload.release();
    }

    @Override
    public boolean release(int decrement) {
        return this.payload == null || this.payload.release(decrement);
    }
}
