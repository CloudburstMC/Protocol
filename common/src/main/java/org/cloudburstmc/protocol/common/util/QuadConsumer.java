package org.cloudburstmc.protocol.common.util;

@FunctionalInterface
public interface QuadConsumer<T, U, V, R> {

    void accept(T t, U u, V v, R r);
}
