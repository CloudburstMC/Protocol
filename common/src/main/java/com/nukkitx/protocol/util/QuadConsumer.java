package com.nukkitx.protocol.util;

@FunctionalInterface
public interface QuadConsumer<T, U, V, R> {

    void accept(T t, U u, V v, R r);
}
