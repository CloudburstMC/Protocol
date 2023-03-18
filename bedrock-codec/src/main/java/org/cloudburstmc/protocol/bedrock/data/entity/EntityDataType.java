package org.cloudburstmc.protocol.bedrock.data.entity;

public class EntityDataType<T> {

    private final String name;
    private final Class<?> type;

    public EntityDataType(Class<? super T> type, String name) {
        this.name = name;
        this.type = type;
    }

    public boolean isInstance(Object value) {
        return type.isInstance(value);
    }

    public String getTypeName() {
        return this.type.getTypeName();
    }

    @Override
    public String toString() {
        return name;
    }
}
