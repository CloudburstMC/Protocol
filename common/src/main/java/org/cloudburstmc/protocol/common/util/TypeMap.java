package org.cloudburstmc.protocol.common.util;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.nukkitx.network.util.Preconditions.checkArgument;
import static com.nukkitx.network.util.Preconditions.checkNotNull;

public final class TypeMap<T> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(TypeMap.class);

    private final String type;
    private final Object2IntMap<T> toId;
    private final Object[] toObject;

    private TypeMap(String type, Object2IntMap<T> toId, Object[] toObject) {
        this.type = type;
        this.toId = toId;
        this.toObject = toObject;
    }

    public int getId(T value) {
        checkNotNull(value, "value");
        return toId.getInt(value);
    }

    @SuppressWarnings("unchecked")
    public T getType(int id) {
        if (id < 0 || id >= toObject.length) {
            log.debug("Unable to find {} for {}", type, id);
            return null;
        }
        return (T) toObject[id];
    }

    public Builder<T> toBuilder() {
        return new Builder<>(type);
    }

    public static <T> Builder<T> builder(Class<T> typeClass) {
        return new Builder<>(typeClass.getSimpleName());
    }

    public static <T> Builder<T> builder(String type) {
        return new Builder<>(type);
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

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<T> {
        private final String type;
        private Object[] types;

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
            checkArgument(this.types[index] == null, "Cannot insert into non-null value");
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
            checkArgument(startIndex < this.types.length, "Start index is out of bounds");
            int length = this.types.length - startIndex;
            this.ensureCapacity(this.types.length + amount);
            System.arraycopy(this.types, startIndex, this.types, startIndex + amount, length);
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

        public Builder<T> remove(int index) {
            return this;
        }

        @SuppressWarnings("unchecked")
        public TypeMap<T> build() {
            Object2IntMap<T> map = new Object2IntOpenHashMap<>();
            for (int i = 0; i < this.types.length; i++) {
                Object type = this.types[i];
                if (type != null) {
                    map.put((T) type, i);
                }
            }
            return new TypeMap<>(this.type, map, Arrays.copyOf(this.types, this.types.length));
        }
    }
}
