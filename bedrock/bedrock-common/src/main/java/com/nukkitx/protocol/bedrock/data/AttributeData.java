package com.nukkitx.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AttributeData {
    String name;
    float minimum;
    float maximum;
    float value;
    float defaultValue;

    public AttributeData(String name, float minimum, float maximum, float value) {
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
        this.value = value;
        this.defaultValue = maximum;
    }
}
