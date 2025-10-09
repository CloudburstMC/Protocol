package org.cloudburstmc.protocol.bedrock.data.entity;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.nbt.NbtUtils;

import java.util.*;

import static org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes.FLAGS;
import static org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes.FLAGS_2;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public final class EntityDataMap implements Map<EntityDataType<?>, Object> {

    public static EnumMap<EntityFlag, Boolean> flagsOf(EntityFlag... flags) {
        EnumMap<EntityFlag, Boolean> map = new EnumMap<>(EntityFlag.class);
        for (EntityFlag flag : flags) {
            map.put(flag, true);
        }
        return map;
    }

    private final Map<EntityDataType<?>, Object> map = new LinkedHashMap<>();

    @NonNull
    public EnumMap<EntityFlag, Boolean> getOrCreateFlags() {
        EnumMap<EntityFlag, Boolean> flags = get(FLAGS);
        if (flags == null) {
            flags = get(FLAGS_2);
            if (flags == null) {
                flags = new EnumMap<>(EntityFlag.class);
            }
            this.putFlags(flags);
        }
        return flags;
    }

    public EnumMap<EntityFlag, Boolean> getFlags() {
        return get(FLAGS);
    }

    public EntityFlag clearFlag(EntityFlag flag) {
        Objects.requireNonNull(flag, "flag");
        EnumMap<EntityFlag, Boolean> flags = this.getOrCreateFlags();
        flags.remove(flag);
        return flag;
    }

    public EntityFlag setFlag(EntityFlag flag, boolean value) {
        Objects.requireNonNull(flag, "flag");
        EnumMap<EntityFlag, Boolean> flags = this.getOrCreateFlags();
        flags.put(flag, value);
        return flag;
    }

    public EnumMap<EntityFlag, Boolean> putFlags(EnumMap<EntityFlag, Boolean> flags) {
        Objects.requireNonNull(flags, "flags");
        this.map.put(FLAGS, flags);
        this.map.put(FLAGS_2, flags);
        return flags;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(EntityDataType<T> type) {
        return (T) this.map.get(type);
    }

    public <T, U extends T> U get(EntityDataType<T> type, Class<U> clazz) {
        return clazz.cast(this.map.get(type));
    }

    @NonNull
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

    @SuppressWarnings("unchecked")
    @Override
    public Object put(EntityDataType<?> key, Object value) {
        checkNotNull(key, "type");
        checkNotNull(value, "value was null for %s", key);
        checkArgument(key.isInstance(value), "value with type %s is not an instance of %s", value.getClass(), key);
        if (key == FLAGS || key == FLAGS_2) {
            return this.putFlags((EnumMap<EntityFlag, Boolean>) value);
        }
        return this.map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(@NonNull Map<? extends EntityDataType<?>, ?> map) {
        checkNotNull(map, "map");
        this.map.putAll(map);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @NonNull
    @Override
    public Set<EntityDataType<?>> keySet() {
        return this.map.keySet();
    }

    @NonNull
    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    @NonNull
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
        while (i.hasNext()) {
            Entry<EntityDataType<?>, Object> e = i.next();
            EntityDataType<?> key = e.getKey();
            if (key == FLAGS_2) continue; // We don't want this to be visible.
            String stringVal = NbtUtils.toString(e.getValue());
            sb.append(key.toString()).append('=').append(stringVal);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
        return sb.toString();
    }
}
