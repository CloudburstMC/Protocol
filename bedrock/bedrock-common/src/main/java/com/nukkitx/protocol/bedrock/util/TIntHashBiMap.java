package com.nukkitx.protocol.bedrock.util;


import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.Objects;

public class TIntHashBiMap<T> {
    private final TIntObjectMap<T> forwards;
    private final TObjectIntMap<T> backwards;

    public TIntHashBiMap() {
        forwards = new TIntObjectHashMap<>(2, 0.5F, -1);
        backwards = new TObjectIntHashMap<>(2, 0.5F, -1);
    }

    public TIntHashBiMap(int initialCapacity) {
        forwards = new TIntObjectHashMap<>(initialCapacity, 0.5F, -1);
        backwards = new TObjectIntHashMap<>(initialCapacity, 0.5F, -1);
    }

    public TIntHashBiMap(int initialCapacity, float loadFactor) {
        forwards = new TIntObjectHashMap<>(initialCapacity, loadFactor, -1);
        backwards = new TObjectIntHashMap<>(initialCapacity, loadFactor, -1);
    }

    public TIntHashBiMap(int initialCapacity, float loadFactor, int noEntryKey) {
        forwards = new TIntObjectHashMap<>(initialCapacity, loadFactor, noEntryKey);
        backwards = new TObjectIntHashMap<>(initialCapacity, loadFactor, noEntryKey);
    }

    public T get(int key) {
        return forwards.get(key);
    }

    public int get(T value) {
        return backwards.get(value);
    }

    public void put(int key, T value) {
        forwards.put(key, value);
        backwards.put(value, key);
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
