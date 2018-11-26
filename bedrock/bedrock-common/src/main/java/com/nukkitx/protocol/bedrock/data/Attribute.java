package com.nukkitx.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@AllArgsConstructor
public class Attribute {
    private String name;
    private final float minimum;
    private final float maximum;
    private final float value;
    @NonFinal
    private float defaultValue;
}
