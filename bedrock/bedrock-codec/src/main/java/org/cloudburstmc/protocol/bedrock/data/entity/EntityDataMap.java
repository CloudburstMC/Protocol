package org.cloudburstmc.protocol.bedrock.data.entity;

import javax.annotation.Nonnull;
import java.util.*;

import static org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes.FLAGS;
import static org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes.FLAGS_2;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public final class EntityDataMap implements Map<EntityDataType<?>, Object> {

    private final Map<EntityDataType<?>, Object> map = new LinkedHashMap<>();

    @Nonnull
    public EnumSet<EntityFlag> getOrCreateFlags() {
        EnumSet<EntityFlag> flags = get(FLAGS);
        if (flags == null) {
            flags = get(FLAGS_2);
            if (flags == null) {
                flags = EnumSet.noneOf(EntityFlag.class);
            }
            this.putFlags(flags);
        }
        return flags;
    }

    public EnumSet<EntityFlag> getFlags() {
        return get(FLAGS);
    }

    public EntityDataMap putFlags(EnumSet<EntityFlag> flags) {
        Objects.requireNonNull(flags, "flags");
        this.map.put(FLAGS, flags);
        this.map.put(FLAGS_2, flags);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(EntityDataType<T> type) {
        return (T) this.map.get(type);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private <T> T getOrDefault(EntityDataType<T> type, T defaultValue) {
        Objects.requireNonNull(type, "type");
        Object object = this.map.getOrDefault(type, defaultValue);
        try {
            return (T) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public <T> void putType(EntityDataType<T> type, T value) {
        this.put(type, value);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    @Override
    public Object put(EntityDataType<?> key, Object value) {
        Objects.requireNonNull(key, "type");
        Objects.requireNonNull(value, "value");
        checkArgument(key.isInstance(value));
        if (key == FLAGS || key == FLAGS_2) {
            return this.putFlags((EnumSet<EntityFlag>) value);
        }
        return this.map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(@Nonnull Map<? extends EntityDataType<?>, ?> map) {
        checkNotNull(map, "map");
        this.map.putAll(map);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Nonnull
    @Override
    public Set<EntityDataType<?>> keySet() {
        return this.map.keySet();
    }

    @Nonnull
    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    @Nonnull
    @Override
    public Set<Entry<EntityDataType<?>, Object>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityDataMap that = (EntityDataMap) o;
        return this.map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        Iterator<Entry<EntityDataType<?>, Object>> i = map.entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry<EntityDataType<?>, Object> e = i.next();
            EntityDataType<?> key = e.getKey();
            if (key == FLAGS_2) continue; // We don't want this to be visible.
            Object value = e.getValue();
            sb.append(key.toString()).append('=').append(value.toString());
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}
