package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.Value;

@Value
public class FloatEntityProperty implements EntityProperty {
    int index;
    float value;
}