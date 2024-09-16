package org.cloudburstmc.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.attribute.AttributeModifierData;

import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor
public class AttributeData {
    String name;
    float minimum;
    float maximum;
    float value;
    float defaultMinimum;
    float defaultMaximum;
    float defaultValue;
    List<AttributeModifierData> modifiers;

    public AttributeData(String name, float minimum, float maximum, float value) {
        this(name, minimum, maximum, value, maximum, Collections.emptyList());
    }

    public AttributeData(String name, float minimum, float maximum, float value, float defaultValue) {
        this(name, minimum, maximum, value, defaultValue, Collections.emptyList());
    }

    public AttributeData(String name, float minimum, float maximum, float value, float defaultValue, List<AttributeModifierData> modifiers) {
        this(name, minimum, maximum, value, minimum, maximum, defaultValue, modifiers);
    }
}
