package com.nukkitx.protocol.bedrock.wrapper;

import com.nukkitx.network.raknet.CustomRakNetPacket;
import com.nukkitx.protocol.PlayerSession;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.session.BedrockSession;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.Collection;

@Data
public class WrappedPacket<T extends PlayerSession> implements CustomRakNetPacket<BedrockSession<T>> {
    private final Collection<BedrockPacket> packets = new ArrayDeque<>();
    private boolean encrypted;
    private ByteBuf batched;
    private ByteBuf payload;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeBytes(payload);
        payload.release();
    }

    @Override
    public void decode(ByteBuf buffer) {
        payload = buffer.readBytes(buffer.readableBytes());
    }

    @Override
    public void handle(BedrockSession<T> session) throws Exception {
        session.onWrappedPacket(this);
    }
}
