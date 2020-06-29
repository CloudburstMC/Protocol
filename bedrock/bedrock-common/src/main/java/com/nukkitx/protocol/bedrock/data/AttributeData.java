package com.nukkitx.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AttributeData {
    private final float defaultValue;
    private final float maximum;
    private final float minimum;
    private final float value;
    private final String name ;

    public AttributeData(String name, float minimum, float maximum, float value) {
        this.defaultValue = maximum;
        this.maximum = maximum;
        this.minimum = minimum;
        this.name = name;
        this.value = value;
    }
}
