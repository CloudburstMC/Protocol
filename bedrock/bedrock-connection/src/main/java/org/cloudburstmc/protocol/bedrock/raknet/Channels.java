package org.cloudburstmc.protocol.bedrock.raknet;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.kqueue.KQueue;

import java.util.concurrent.ThreadFactory;

public class Channels extends Platform {
    private static final ThreadFactory bedrockThreadFactory = CloudThreadFactory.builder()
            .format("Bedrock Listener - #%d").daemon(true).build();
    private static final ThreadFactory networkThreadFactory = CloudThreadFactory.builder()
            .format("RakNet Listener - #%d").daemon(true).priority(Thread.MAX_PRIORITY).build();

    private static final ChannelImplementation defaultImplementation;
    private static EventLoopGroup commonGroup;

    static {
        // TODO: IO_URING
        boolean disableNative = System.getProperties().contains("disableNativeEventLoop");

        if (!disableNative && Epoll.isAvailable()) {
            defaultImplementation = ChannelImplementation.EPOLL;
        } else if (!disableNative && KQueue.isAvailable()) {
            defaultImplementation = ChannelImplementation.KQUEUE;
        } else {
            defaultImplementation = ChannelImplementation.NIO;
        }
    }

    public static ChannelImplementation defaultImplementation() {
        return defaultImplementation;
    }

    public static EventLoopGroup newEventLoopGroup(int threads, boolean network) {
        // return new RakEventLoopGroup(childGroup);
        return defaultImplementation.newEventLoopGroup(threads, network ? networkThreadFactory : bedrockThreadFactory);
    }

    public static EventLoopGroup commonGroup() {
        if (commonGroup == null) {
            // Initialize only if accessed
            commonGroup = newEventLoopGroup(0, false);
        }
        return commonGroup;
    }
}
