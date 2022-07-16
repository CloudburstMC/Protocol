package com.nukkitx.protocol.bedrock.data.attribute;

import lombok.Value;

@Value
public class AttributeModifierData {
    /**
     * UUID of the modifier
     */
    String id;
    String name;
    float amount;
    AttributeOperation operation;
    int operand;
    boolean serializable;
}
