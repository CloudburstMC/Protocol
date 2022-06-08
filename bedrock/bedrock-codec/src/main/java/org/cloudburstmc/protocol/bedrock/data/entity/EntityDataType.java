package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.ToString;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@ToString
public abstract class EntityDataType<T> {

    private final Type type;

    public EntityDataType() {
        this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public boolean isInstance(Object value) {
        if (type instanceof Class) {
            return ((Class<?>) type).isInstance(value);
        } else if (type instanceof ParameterizedType) {
            return ((Class<?>) ((ParameterizedType) type).getRawType()).isInstance(value);
        }
        return false;
    }

    public String getTypeName() {
        return this.type.getTypeName();
    }
}
