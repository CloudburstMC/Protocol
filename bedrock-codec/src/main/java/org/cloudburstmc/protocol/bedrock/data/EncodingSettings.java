package org.cloudburstmc.protocol.bedrock.data;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@Builder(builderClassName = "Builder", toBuilder = true)
public class EncodingSettings {
    public static final EncodingSettings DEFAULT = EncodingSettings.builder()
            .maxListSize(1024)
            .maxByteArraySize(1024 * 1024) // 1MB
            .build();

    private final int maxListSize;
    private final int maxByteArraySize;
}
