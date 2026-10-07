package org.cloudburstmc.protocol.bedrock.data.definitions;

import lombok.Value;

import java.util.UUID;

@Value
public class DimensionDefinition {
    String id;
    int maximumHeight;
    int minimumHeight;
    int generatorType;
    /**
     * @since v975
     */
    int dimensionType;
    /**
     * @since v2168
     */
    UUID packId;
    /**
     * @since v2192
     */
    String defaultBiome;
    /**
     * @since v2225
     */
    int cloudHeight;
    /**
     * @since v2225
     */
    boolean renderClouds;
}
