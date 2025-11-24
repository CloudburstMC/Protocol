package org.cloudburstmc.protocol.bedrock.transformer;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.EnumMap;
import java.util.Map;

/**
 * Transforms entity flags between their EnumMap representation and a packed long value.
 * <p>
 * Entity flags are split into multiple groups of 64 flags each (FLAGS, FLAGS_2).
 * Each transformer instance handles one group, identified by its index:
 * - index 0 = FLAGS (flags 0-63)
 * - index 1 = FLAGS_2 (flags 64-127)
 * <p>
 */
@RequiredArgsConstructor
public final class FlagTransformer implements EntityDataTransformer<Long, EnumMap<EntityFlag, Boolean>> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(FlagTransformer.class);

    private final TypeMap<EntityFlag> typeMap;
    /**
     * The index of this flag group (0 for FLAGS, 1 for FLAGS_2)
     */
    private final int index;

    /**
     * Serializes entity flags into a packed long value for network transmission.
     * <p>
     * Only flags within this transformer's range (determined by index) are processed.
     * For example, if index=0, only flags 0-63 are handled; flags 64+ are ignored.
     * <p>
     * Returns null if no flags in this group are present, preventing unnecessary
     * serialization of empty flag groups. This fixes the issue where missing flag
     * groups would be written as zero, causing clients to incorrectly clear flags.
     *
     * @param helper the codec helper
     * @param map the entity data map
     * @param flags the complete flag map
     * @return the packed long value containing flags for this group, or null if no flags exist
     */
    @Override
    public Long serialize(BedrockCodecHelper helper, EntityDataMap map, EnumMap<EntityFlag, Boolean> flags) {
        long value = 0;
        // Calculate the range of flag indices this transformer handles
        int lower = this.index * 64;
        int upper = lower + 64;
        // Track whether any flags in this range exist (even if set to false)
        boolean exists = false;

        for (Map.Entry<EntityFlag, Boolean> entry : flags.entrySet()) {
            EntityFlag flag = entry.getKey();
            Boolean data = entry.getValue();
            if (data == null) {
                continue;
            }

            int flagIndex = this.typeMap.getId(flag);
            if (flagIndex < lower || flagIndex >= upper) {
                // This flag belongs to a different transformer (different index)
                continue;
            }

            // Mark that at least one flag in this range exists
            // This ensures we return a value (even if 0) rather than null
            exists = true;

            if (data) {
                // Set the bit at the appropriate position (0-63 within this group)
                // The & 0x3f masks the flag index to get its position within the 64-bit range
                value |= 1L << (flagIndex & 0x3f);
            }
            // If data is false, the bit remains 0 (default), but we still write the group
        }

        // Return null if no flags in this range exist
        // This prevents writing unnecessary flag groups and fixes unintentional flag resets on client
        if (exists) {
            return value;
        }
        return null;
    }

    /**
     * Deserializes a packed long value into individual entity flags.
     * <p>
     * Reads all 64 possible flag positions for this group, setting each flag
     * to true or false based on the corresponding bit in the value.
     *
     * @param helper the codec helper
     * @param map the entity data map to populate
     * @param value the packed long value containing flag states
     * @return the updated flag map
     */
    @Override
    public EnumMap<EntityFlag, Boolean> deserialize(BedrockCodecHelper helper, EntityDataMap map, Long value) {
        EnumMap<EntityFlag, Boolean> flags = map.getOrCreateFlags();

        // Calculate the range of flag indices this transformer handles
        int lower = this.index * 64;
        int upper = lower + 64;

        // Iterate through all 64 possible flags in this group
        for (int i = lower; i < upper; i++) {
            // Get the bit position within this 64-bit group (0-63)
            int idx = i & 0x3f;

            EntityFlag flag = this.typeMap.getTypeUnsafe(i);

            // Check if the bit at this position is set
            boolean set = (value & (1L << idx)) != 0;

            if (flag == null) {
                // Only log "Unknown entity flag" if the bit is actually set (1).
                // If the bit is 0, it is likely just unused padding at the end of the long 
                // (e.g., indices 100-127 when only 100 flags are defined).
                if (set) {
                    log.debug("Unknown entity flag set to true detected with index {}", i);
                }
                continue;
            }

            if (set) {
                flags.put(flag, true);
            } else {
                flags.put(flag, false);
            }
        }

        return flags;
    }
}