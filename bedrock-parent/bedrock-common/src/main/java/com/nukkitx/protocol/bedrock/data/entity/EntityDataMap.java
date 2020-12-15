package com.nukkitx.protocol.bedrock.data.entity;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static com.nukkitx.network.util.Preconditions.checkNotNull;
import static com.nukkitx.protocol.bedrock.data.entity.EntityData.FLAGS;
import static com.nukkitx.protocol.bedrock.data.entity.EntityData.FLAGS_2;

public class EntityDataMap implements Map<EntityData, Object> {

    private final Map<EntityData, Object> map = new LinkedHashMap<>();

    public byte getByte(EntityData key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(EntityData key, byte defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putByte(EntityData key, int value) {
        this.put(key, (byte) value);
        return this;
    }

    public short getShort(EntityData key) {
        return getShort(key, (short) 0);
    }

    public short getShort(EntityData key, short defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putShort(EntityData key, int value) {
        this.put(key, (short) value);
        return this;
    }

    public int getInt(EntityData key) {
        return getInt(key, 0);
    }

    public int getInt(EntityData key, int defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putInt(EntityData key, int value) {
        this.put(key, value);
        return this;
    }

    public float getFloat(EntityData key) {
        return getFloat(key, 0f);
    }

    public float getFloat(EntityData key, float defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putFloat(EntityData key, float value) {
        this.put(key, value);
        return this;
    }

    public String getString(EntityData key) {
        return getString(key, "");
    }

    public String getString(EntityData key, String defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putString(EntityData key, String value) {
        this.put(key, value);
        return this;
    }

    public NbtMap getTag(EntityData key) {
        return getTag(key, NbtMap.EMPTY);
    }

    public NbtMap getTag(EntityData key, NbtMap defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putTag(EntityData key, NbtMap value) {
        this.put(key, value);
        return this;
    }

    public Vector3i getPos(EntityData key) {
        return getPos(key, Vector3i.ZERO);
    }

    public Vector3i getPos(EntityData key, Vector3i defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putPos(EntityData key, Vector3i value) {
        this.put(key, value);
        return this;
    }

    public long getLong(EntityData key) {
        return getLong(key, 0);
    }

    public long getLong(EntityData key, long defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putLong(EntityData key, long value) {
        this.put(key, value);
        return this;
    }

    public Vector3f getVector3f(EntityData key) {
        return getVector3f(key, Vector3f.ZERO);
    }

    public Vector3f getVector3f(EntityData key, Vector3f defaultValue) {
        return getOrDefault(key, defaultValue);
    }

    public EntityDataMap putVector3f(EntityData key, Vector3f value) {
        this.put(key, value);
        return this;
    }

    @Nonnull
    public EntityFlags getOrCreateFlags() {
        EntityFlags flags = this.getFlags();
        if (flags == null) {
            this.putFlags(flags = new EntityFlags());
        }
        return flags;
    }

    public EntityFlags getFlags() {
        return (EntityFlags) this.map.get(FLAGS);
    }

    public EntityDataMap putFlags(EntityFlags flags) {
        Objects.requireNonNull(flags, "flags");
        this.map.put(FLAGS, flags);
        this.map.put(FLAGS_2, flags);
        return this;
    }

    @Nullable
    public EntityData.Type getType(EntityData key) {
        Object value = this.map.get(key);
        if (value == null) {
            return null;
        }
        return EntityData.Type.from(value);
    }

    @Nullable
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public <T> T ensureAndGet(Object key) {
        return (T) this.map.get(key);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private <T> T getOrDefault(EntityData key, T defaultValue) {
        Objects.requireNonNull(key, "key");
        Object object = this.map.getOrDefault(key, defaultValue);
        try {
            return (T) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
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
        return null;
    }

    @Override
    public Object put(EntityData key, Object value) {
        Objects.requireNonNull(key, "type");
        Objects.requireNonNull(value, "value");
        //noinspection ResultOfMethodCallIgnored
        EntityData.Type.from(value); // make sure the value is legal.
        if (key == FLAGS || key == FLAGS_2) {
            return this.putFlags((EntityFlags) value);
        }
        return this.map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(@Nonnull Map<? extends EntityData, ?> map) {
        checkNotNull(map, "map");
        this.map.putAll(map);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Nonnull
    @Override
    public Set<EntityData> keySet() {
        return this.map.keySet();
    }

    @Nonnull
    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    @Nonnull
    @Override
    public Set<Entry<EntityData, Object>> entrySet() {
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
        return map.toString();
    }

    private static String mapToString(Map<EntityData, Object> map) {
        Iterator<Entry<EntityData, Object>> i = map.entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry<EntityData, Object> e = i.next();
            EntityData key = e.getKey();
            if (key == FLAGS_2) continue; // We don't want this to be visible.
            Object value = e.getValue();
            sb.append(key.toString()).append('=').append(value.toString());
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}
