package org.cloudburstmc.protocol.bedrock.codec;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataType;
import org.cloudburstmc.protocol.bedrock.transformer.EntityDataTransformer;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityDataTypeMap {

    private static final EntityDataFormat[] FORMATS = EntityDataFormat.values();

    private final Definition<?>[][][] idDefinitions;
    private final Map<EntityDataType<?>, Definition<?>> typeDefinitionMap;

    public Definition<?>[] fromId(int id, EntityDataFormat format) {
        if (id >= 0 && id < this.idDefinitions.length && format != null) {
            Definition<?>[][] definitions = this.idDefinitions[id];
            int formatId = format.ordinal();
            if (definitions != null && formatId < definitions.length) {
                return definitions[formatId];
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> Definition<T> fromType(EntityDataType<T> type) {
        return (Definition<T>) this.typeDefinitionMap.get(type);
    }

    public Builder toBuilder() {
        return new Builder(copy(this.idDefinitions, false));
    }

    public static Builder builder() {
        return new Builder(new Definition[64][][]);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Definition<T> {
        int id;
        final EntityDataType<T> type;
        EntityDataTransformer<?, T> transformer;
        final EntityDataFormat format;
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

    private static Definition<?>[][][] copy(Definition<?>[][][] array, boolean compact) {
        Definition<?>[][][] copy = new Definition[lastNonNullIndex(array)][][];
        for (int i = 0; i < copy.length; i++) {
            if (array[i] == null) continue;
            copy[i] = new Definition[compact ? lastNonNullIndex(array) : FORMATS.length][];
            for (int i2 = 0; i2 < copy[i].length && i2 < array[i].length; i2++) {
                if (array[i][i2] == null) continue;
                int length = array[i][i2].length;
                copy[i][i2] = new Definition[length];
                System.arraycopy(array[i][i2], 0, copy[i][i2], 0, length);
            }
        }
        return copy;
    }

    private static <T> int lastNonNullIndex(T[] array) {
        int index = array.length - 1;
        while (array[index] == null && index > 0) {
            index--;
        }
        return index;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private Definition<?>[][][] types;
        private final Map<EntityDataType<?>, Definition<?>> typeDefinitionMap = new IdentityHashMap<>();

        private void ensureIndex(int index) {
            ensureCapacity(index + 1);
        }

        private void ensureCapacity(int size) {
            if (size > this.types.length) {
                int newSize = powerOfTwoCeiling(size + 1);
                Definition<?>[][][] newTypes = new Definition[newSize][][];
                System.arraycopy(types, 0, newTypes, 0, this.types.length);
                this.types = newTypes;
            }
        }

        public <T> Builder replace(EntityDataType<T> type, int index, EntityDataFormat format) {
            return replace(type, index, format, EntityDataTransformer.identity());
        }

        /**
         * Replaces all types with any format at the specified index.
         *
         * @param type   entity data type
         * @param index  index of the type
         * @param format format of the type
         * @param <T>    type of the entity data
         * @return this builder
         */
        public <T> Builder replace(EntityDataType<T> type, int index, EntityDataFormat format, EntityDataTransformer<?, T> transformer) {
            checkArgument(index < this.types.length, "Index is out of bounds");
            Definition<?>[][] formats = this.types[index];

            checkArgument(formats != null, "No data types to replace at %s", index);

            for (Definition<?>[] definitions : formats) {
                if (definitions != null) {
                    for (Definition<?> definition : definitions) {
                        if (definition != null) {
                            this.typeDefinitionMap.remove(definition.type);
                        }
                    }
                }
            }

            return insert(type, index, format, transformer);
        }

        public <T> Builder insert(EntityDataType<T> type, int index, EntityDataFormat format) {
            return insert(type, index, format, EntityDataTransformer.identity());
        }

        public <T> Builder insert(EntityDataType<T> type, int index, EntityDataFormat format,
                                  EntityDataTransformer<?, T> transformer) {
            checkNotNull(type, "type");
            checkNotNull(transformer, "transformer");
            checkNotNull(format, "format");
            checkArgument(index > 0, "index cannot be negative");
            checkArgument(!this.typeDefinitionMap.containsKey(type), "type already defined");

            this.ensureIndex(index + 1);

            if (this.types[index] == null) {
                this.types[index] = new Definition[FORMATS.length][];
            }
            Definition<?>[][] formats = this.types[index];
            int formatId = format.ordinal();

            Definition<?>[] definitions = formats[formatId];
            if (definitions == null) {
                definitions = formats[formatId] = new Definition[1];
            } else {
                definitions = formats[formatId] = Arrays.copyOf(definitions, definitions.length + 1);
            }

            Definition<?> definition = definitions[definitions.length - 1] = new Definition<T>(index, type, transformer, format);
            this.typeDefinitionMap.put(type, definition);

            return this;
        }

        /**
         * Shifts values from a specific start index
         *
         * @param startIndex index to start shifting from
         * @param delta      the delta to shift the types by
         * @return this builder
         */
        public Builder shift(int startIndex, int delta) {
            return shift(startIndex, delta, this.types.length - startIndex);
        }

        /**
         * Shifts values from a specific start index
         *
         * @param startIndex index to start shifting from
         * @param delta      the delta to shift the types by
         * @param length     length to shift
         * @return this builder
         */
        public Builder shift(int startIndex, int delta, int length) {
            checkArgument(startIndex < this.types.length, "Start index is out of bounds");
            int endIndex = startIndex + length;
            checkArgument(endIndex <= this.types.length, "Length exceeds array bounds");
            this.ensureCapacity(this.types.length + delta);
            System.arraycopy(this.types, startIndex, this.types, startIndex + delta, length);

            // Clear old values
            for (int i = 0; i < delta; i++) {
                this.types[startIndex + i] = null;
            }

            for (int i = 0; i < length; i++) {
                int index = startIndex + delta + i;
                Definition<?>[][] formats = this.types[index];
                if (formats == null) continue;
                for (Definition<?>[] format : formats) {
                    if (format == null) continue;
                    for (Definition<?> definition : format) {
                        if (definition == null) continue;
                        definition.id = index;
                    }
                }
            }
            return this;
        }

        public Builder remove(int index) {
            checkElementIndex(index, this.types.length);
            checkArgument(this.types[index] != null, "Cannot remove null value");

            Definition<?>[][] formats = this.types[index];
            for (Definition<?>[] format : formats) {
                if (format == null) continue;
                for (Definition<?> definition : format) {
                    if (definition == null) continue;
                    this.typeDefinitionMap.remove(definition.type);
                }
            }

            this.types[index] = null;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> Builder update(EntityDataType<T> type, EntityDataTransformer<?, T> transformer) {
            checkNotNull(type, "type");
            checkNotNull(transformer, "transformer");

            Definition<T> definition = (Definition<T>) this.typeDefinitionMap.get(type);
            checkArgument(definition != null, "type not defined");

            definition.transformer = transformer;

            return this;
        }

        public EntityDataTypeMap build() {
            return new EntityDataTypeMap(copy(this.types, true), this.typeDefinitionMap);
        }
    }
}
