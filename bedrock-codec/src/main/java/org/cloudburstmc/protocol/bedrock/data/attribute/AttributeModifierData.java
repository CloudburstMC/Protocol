package org.cloudburstmc.protocol.bedrock.data.attribute;

import lombok.Value;

@Value
public class AttributeModifierData {
    String id;
    String name;
    float amount;
    AttributeOperation operation;
    int operand;
    boolean serializable;
}