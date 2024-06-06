package org.cloudburstmc.protocol.bedrock.data;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@Builder(builderClassName = "Builder", toBuilder = true)
public class EncodingSettings {
    /**
     * A default {@link EncodingSettings} instance.
     * This instance sets limits that should be generally safe for most use cases,
     * but there may be edge cases where it is recommended to use either CLIENT OR SERVER settings
     * based on the context.
     */
    public static final EncodingSettings DEFAULT = EncodingSettings.builder()
            .maxListSize(1536)
            .maxByteArraySize(1024 * 1024) // 1MB
            .maxNetworkNBTSize(1024 * 1024 * 512) // 1.5MB
            .maxItemNBTSize(1024 * 100) // 100KB
            .maxStringLength(1024 * 32) // 32KB
            .build();

    /**
     * A {@link EncodingSettings} instance for Bedrock Client implementation.
     * This instance sets limits that less strict because client is generally expected to
     * be able to receive larger packets and NBT data.
     */
    public static final EncodingSettings CLIENT = EncodingSettings.builder()
            .maxListSize(10240)
            .maxByteArraySize(1024 * 1024 * 20) // 20MB
            .maxNetworkNBTSize(1024 * 1024 * 10) // 10MB
            .maxItemNBTSize(1024 * 1024 * 5) // 5MB
            .maxStringLength(1024 * 1024 * 2) // 2MB
            .build();

    /**
     * A {@link EncodingSettings} instance for Bedrock Server implementation.
     * This instance sets limits that are stricter because server is generally not expected to
     * receive large packets or NBT data.
     */
    public static final EncodingSettings SERVER = EncodingSettings.builder()
            .maxListSize(1024)
            .maxByteArraySize(1024 * 512) // 500KB
            .maxNetworkNBTSize(1024 * 512) // 500KB
            .maxItemNBTSize(1024 * 100) // 100KB
            .maxStringLength(1024 * 32) // 32KB
            .build();

    private final int maxListSize;
    private final int maxByteArraySize;
    private final int maxNetworkNBTSize;
    private final int maxItemNBTSize;
    private final int maxStringLength;
}
