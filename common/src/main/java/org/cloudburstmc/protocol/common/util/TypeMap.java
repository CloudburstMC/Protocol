package org.cloudburstmc.protocol.common.util;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import static org.cloudburstmc.protocol.common.util.Preconditions.*;

public final class TypeMap<T> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(TypeMap.class);

    private final String type;
    private final Object2IntMap<T> toId;
    private final Int2ObjectMap<T> toObject;

    private TypeMap(String type, Object2IntMap<T> toId, Int2ObjectMap<T> toObject) {
        this.type = type;
        this.toId = toId;
        this.toObject = toObject;
    }

    public int getId(T value) {
        checkNotNull(value, "value");
        return toId.getInt(value);
    }

    public T getType(int id) {
        return toObject.get(id);
    }

    public Builder<T> toBuilder() {
        Builder<T> builder = new Builder<>(type);
        this.toObject.forEach(builder::insert);
        return builder;
    }

    public void forEach(BiConsumer<Integer, T> consumer) {
        this.toObject.forEach(consumer);
    }

    public static <T> Builder<T> builder(Class<T> typeClass) {
        return new Builder<>(typeClass.getSimpleName());
    }

    public static <T> Builder<T> builder(String type) {
        return new Builder<>(type);
    }

    public static <T> TypeMap<T> empty(Class<T> typeClass) {
        return empty(typeClass.getSimpleName());
    }

    public static <T> TypeMap<T> empty(String type) {
        return new TypeMap<T>(type, Object2IntMaps.emptyMap(), Int2ObjectMaps.emptyMap());
    }

    private static int powerOfTwoCeiling(int value) {
        value--;
        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;
        value++;
        return value;
    }

    public String prettyPrint() {
        TreeMap<Integer, T> map = new TreeMap<>(this.toObject);

        StringJoiner joiner = new StringJoiner("\n");
        for (Map.Entry<Integer, T> entry : map.entrySet()) {
            joiner.add(entry.getKey() + " => " + entry.getValue());
        }
        return joiner.toString();
    }

    public static <T extends Enum<T>> TypeMap<T> fromEnum(Class<T> clazz) {
        return fromEnum(clazz, -1);
    }

    public static <T extends Enum<T>> TypeMap<T> fromEnum(Class<T> clazz, int maxIndex) {
        EnumSet<T> values = EnumSet.allOf(clazz);

        Builder<T> builder = builder(clazz);
        for (T value : values) {
            if (maxIndex != -1 && value.ordinal() > maxIndex) {
                break;
            }
            builder.insert(value.ordinal(), value);
        }
        return builder.build();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<T> {
        private final String type;
        private Object[] types = new Object[0];

        private void ensureIndex(int index) {
            ensureCapacity(index + 1);
        }

        private void ensureCapacity(int size) {
            if (size > this.types.length) {
                int newSize = powerOfTwoCeiling(size + 1);
                Object[] newTypes = new Object[newSize];
                System.arraycopy(types, 0, newTypes, 0, this.types.length);
                this.types = newTypes;
            }
        }

        public Builder<T> insert(int index, T value) {
            checkNotNull(value, "value");
            this.ensureIndex(index);
            checkArgument(this.types[index] == null, "Cannot insert into non-null value at index " + index);
            this.types[index] = value;
            return this;
        }

        /**
         * Shifts values from a specific start index
         *
         * @param startIndex
         * @param amount
         * @return
         */
        public Builder<T> shift(int startIndex, int amount) {
            return shift(startIndex, amount, this.types.length - startIndex);
        }

        /**
         * Shifts values from a specific start index
         *
         * @param startIndex
         * @param amount
         * @param length
         * @return
         */
        public Builder<T> shift(int startIndex, int amount, int length) {
            checkArgument(startIndex < this.types.length, "Start index is out of bounds");
            int endIndex = startIndex + length;
            checkArgument(endIndex <= this.types.length, "Length exceeds array bounds");
            this.ensureCapacity(this.types.length + amount);
            System.arraycopy(this.types, startIndex, this.types, startIndex + amount, length);

            // Clear old values
            for (int i = 0; i < amount; i++) {
                this.types[startIndex + i] = null;
            }
            return this;
        }

        /**
         * Replaces an existing value in type map
         *
         * @param index
         * @param value
         * @return
         * @throws IllegalArgumentException if value does not exist in type map
         */
        public Builder<T> replace(int index, T value) {
            checkNotNull(value, "value");
            checkArgument(index < this.types.length, "Cannot update out of bounds value");
            checkArgument(this.types[index] != null, "Cannot update null value");
            this.types[index] = value;
            return this;
        }

        public Builder<T> update(int oldIndex, int newIndex, T value) {
            checkNotNull(value, "value");
            checkArgument(oldIndex < this.types.length, "Cannot update out of bounds value");
            checkArgument(this.types[oldIndex] == value, "oldIndex value does not equal expected");
            this.ensureIndex(newIndex);
            this.types[oldIndex] = null;
            this.types[newIndex] = value;
            return this;
        }

        public Builder<T> insert(int offset, TypeMap<? extends T> map) {
            checkNotNull(map, "map");
            map.toObject.forEach((index, value) -> {
                int newIndex = index + offset;
                checkNotNull(value, "value");
                this.ensureIndex(newIndex);
                this.types[newIndex] = value;
            });
            return this;
        }

        public Builder<T> remove(int index) {
            checkElementIndex(index, this.types.length);
            // checkArgument(this.types[index] != null, "Cannot remove null value");
            this.types[index] = null;
            return this;
        }

        @SuppressWarnings("unchecked")
        public TypeMap<T> build() {
            Object2IntMap<T> toId = new Object2IntOpenHashMap<>();
            Int2ObjectMap<T> toObject = new Int2ObjectOpenHashMap<>();
            for (int i = 0; i < this.types.length; i++) {
                Object type = this.types[i];
                if (type != null) {
                    toId.put((T) type, i);
                    toObject.put(i, (T) type);
                }
            }
            return new TypeMap<>(this.type, toId, toObject);
        }
    }
}
