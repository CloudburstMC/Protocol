package org.cloudburstmc.protocol.java;

import com.nukkitx.protocol.MinecraftSession;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;

import java.net.InetSocketAddress;

public class JavaClientSession extends JavaSession implements MinecraftSession<JavaPacket<?>> {
    JavaClientSession(InetSocketAddress address, Channel channel, EventLoop eventLoop) {
        super(address, channel, eventLoop);
    }
}
