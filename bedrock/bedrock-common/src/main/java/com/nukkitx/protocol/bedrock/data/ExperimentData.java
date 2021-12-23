package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class ExperimentData {
    private final String name;
    private final boolean enabled; // ??? Always set to true
}
