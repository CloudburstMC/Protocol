package org.cloudburstmc.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AttributeData {
    private final String name;
    private final float minimum;
    private final float maximum;
    private final float value;
    private final float defaultValue;

    public AttributeData(String name, float minimum, float maximum, float value) {
        this.name = name;
        this.minimum = minimum;
        this.maximum = maximum;
        this.value = value;
        this.defaultValue = maximum;
    }
}
