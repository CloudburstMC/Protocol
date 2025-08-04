package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EntityDataFormat {
    BYTE,
    SHORT,
    INT,
    FLOAT,
    STRING,
    COMPONENT,
    NBT,
    VECTOR3I,
    LONG,
    VECTOR3F;
}
