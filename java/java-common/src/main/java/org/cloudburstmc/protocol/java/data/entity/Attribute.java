package org.cloudburstmc.protocol.java.data.entity;

import lombok.Value;

import java.util.List;

@Value
public class Attribute {
    AttributeType type;
    double value;
    List<AttributeModifier> modifiers;
}
