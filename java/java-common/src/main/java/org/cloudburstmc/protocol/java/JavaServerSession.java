package org.cloudburstmc.protocol.java;

import com.nukkitx.protocol.MinecraftServerSession;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;

import java.net.InetSocketAddress;

public class JavaServerSession extends JavaSession implements MinecraftServerSession<JavaPacket<?>> {
    JavaServerSession(InetSocketAddress address, Channel channel, EventLoop eventLoop) {
        super(address, channel, eventLoop);
    }
}
