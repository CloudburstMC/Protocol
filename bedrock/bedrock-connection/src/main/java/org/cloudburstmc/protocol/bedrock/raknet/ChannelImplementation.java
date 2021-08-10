package org.cloudburstmc.protocol.bedrock.raknet;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueueDatagramChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public enum ChannelImplementation {
    EPOLL(EpollDatagramChannel.class, EpollSocketChannel.class, EpollServerSocketChannel.class, EpollEventLoopGroup::new),
    KQUEUE(KQueueDatagramChannel.class, KQueueSocketChannel.class, KQueueServerSocketChannel.class, KQueueEventLoopGroup::new),
    NIO(NioDatagramChannel.class, NioSocketChannel.class, NioServerSocketChannel.class, NioEventLoopGroup::new);

    private final Class<? extends DatagramChannel> datagramChannel;
    private final Class<? extends SocketChannel> socketChannel;
    private final Class<? extends ServerSocketChannel> serverSocketChannel;
    private final BiFunction<Integer, ThreadFactory, EventLoopGroup> eventLoopGroupFactory;

    public EventLoopGroup newEventLoopGroup(int threads, ThreadFactory threadFactory) {
        return this.eventLoopGroupFactory.apply(threads, threadFactory);
    }

    public Class<? extends DatagramChannel> datagramChannel() {
        return this.datagramChannel;
    }

    public Class<? extends SocketChannel> socketChannel() {
        return this.socketChannel;
    }

    public Class<? extends ServerSocketChannel> serverSocketChannel() {
        return this.serverSocketChannel;
    }
}
