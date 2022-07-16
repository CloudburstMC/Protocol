package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.protocol.bedrock.data.attribute.AttributeModifierData;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor
public class AttributeData {
    private final String name;
    private final float minimum;
    private final float maximum;
    private final float value;
    private final float defaultValue;
    private final List<AttributeModifierData> modifiers;

    public AttributeData(String name, float minimum, float maximum, float value) {
        this(name, minimum, maximum, value, maximum, Collections.emptyList());
    }

    public AttributeData(String name, float minimum, float maximum, float value, float defaultValue) {
        this(name, minimum, maximum, value, defaultValue, Collections.emptyList());
    }
}
