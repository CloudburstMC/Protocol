package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.Value;

@Value
public class IntEntityProperty implements EntityProperty {
    int index;
    int value;
}