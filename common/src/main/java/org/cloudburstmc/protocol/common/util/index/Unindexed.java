package org.cloudburstmc.protocol.common.util.index;

import java.util.Objects;

public class Unindexed<T> implements Indexable<T> {

    private final T value;

    public Unindexed(T value) {
        this.value = Objects.requireNonNull(value, "value cannot be null");
    }

    @Override
    public T get() {
        return value;
    }
}
