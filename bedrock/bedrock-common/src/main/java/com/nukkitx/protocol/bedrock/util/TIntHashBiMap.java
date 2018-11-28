package com.nukkitx.protocol.bedrock.util;


import com.nukkitx.network.util.Preconditions;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.Objects;

public class TIntHashBiMap<T> {
    private final TIntObjectMap<T> forwards;
    private final TObjectIntMap<T> backwards;
    private final T noEntryValue;

    public TIntHashBiMap() {
        this(2);
    }

    public TIntHashBiMap(int initialCapacity) {
        this(initialCapacity, 0.5F);
    }

    public TIntHashBiMap(T noEntryValue) {
        this(2, 0.5F, -1, noEntryValue);
    }

    public TIntHashBiMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, -1);
    }

    public TIntHashBiMap(int initialCapacity, float loadFactor, int noEntryKey) {
        this(initialCapacity, loadFactor, noEntryKey, null);
    }

    public TIntHashBiMap(int initialCapacity, float loadFactor, int noEntryKey, T noEntryValue) {
        forwards = new TIntObjectHashMap<>(initialCapacity, loadFactor, noEntryKey);
        backwards = new TObjectIntHashMap<>(initialCapacity, loadFactor, noEntryKey);
        this.noEntryValue = noEntryValue;
    }

    public T get(int key) {
        T value = forwards.get(key);
        if (value == null) {
            value = noEntryValue;
        }
        return value;
    }

    public int get(T value) {
        Preconditions.checkNotNull(value, "value");
        return backwards.get(value);
    }

    public void put(int key, T value) {
        Preconditions.checkNotNull(value, "value");
        forwards.put(key, value);
        backwards.put(value, key);
    }

    public boolean containsKey(int key) {
        return forwards.containsKey(key);
    }

    public boolean containsValue(T value) {
        return forwards.containsValue(value);
    }

    public boolean remove(int key) {
        if (!forwards.containsKey(key)) {
            return false;
        }

        T value = forwards.get(key);
        if (!backwards.containsKey(value)) {
            return false;
        }
        forwards.remove(key);
        backwards.remove(value);
        return true;
    }

    @Override
    public int hashCode() {
        return forwards.hashCode();
    }

    @Override
    public String toString() {
        return forwards.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TIntHashBiMap that = (TIntHashBiMap) o;
        return Objects.equals(forwards, that.forwards) && Objects.equals(backwards, that.backwards);
    }
}
