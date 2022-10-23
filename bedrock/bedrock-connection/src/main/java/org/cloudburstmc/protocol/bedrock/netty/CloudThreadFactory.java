package org.cloudburstmc.protocol.bedrock.netty;

import lombok.Builder;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
public class CloudThreadFactory implements ThreadFactory {
    private static final ThreadFactory backingFactory = Executors.defaultThreadFactory();

    private final AtomicInteger counter = new AtomicInteger(0);
    private final boolean daemon;
    private final String format;
    @Builder.Default
    private final int priority = Thread.currentThread().getPriority();
    private final Thread.UncaughtExceptionHandler exceptionHandler;

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = backingFactory.newThread(runnable);
        if (this.format != null) {
            thread.setName(String.format(Locale.ROOT, this.format, this.counter.getAndIncrement()));
        }

        if (this.exceptionHandler != null) {
            thread.setUncaughtExceptionHandler(this.exceptionHandler);
        }

        thread.setDaemon(this.daemon);
        thread.setPriority(this.priority);
        return thread;
    }
}
