package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.util.TIntHashBiMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ToString
public class MetadataFlags {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(MetadataFlags.class);

    private final Set<Metadata.Flag> flags = new HashSet<>();

    public static MetadataFlags create(long value, TIntHashBiMap<Metadata.Flag> flagMappings) {
        MetadataFlags flags = new MetadataFlags();
        for (int i = 0; i < 64; i++) {
            if ((value & (1 << i)) != 0) {
                Metadata.Flag flag = flagMappings.get(i);
                if (flag != null) {
                    flags.flags.add(flag);
                } else {
                    log.debug("Unknown Metadata flag index {} detected", i);
                }
            }
        }
        return flags;
    }

    /**
     * Set {@link Metadata.Flag} value
     *
     * @param flag  flag to be set
     * @param value value to be set to
     * @return whether there was a change
     */
    public synchronized boolean setFlag(@Nonnull Metadata.Flag flag, boolean value) {
        Preconditions.checkNotNull(flag, "flag");
        boolean contains = flags.contains(flag);
        if (contains && !value) {
            flags.remove(flag);
            return true;
        } else if (!contains && value) {
            flags.add(flag);
            return true;
        }
        return false;
    }

    /**
     * Get {@link Metadata.Flag} value
     *
     * @param flag flag to get
     * @return value of flag
     */
    public synchronized boolean getFlag(@Nonnull Metadata.Flag flag) {
        Preconditions.checkNotNull(flag, "flag");
        return flags.contains(flag);
    }

    public synchronized long get(TIntHashBiMap<Metadata.Flag> flagMappings) {
        long value = 0;
        for (Metadata.Flag flag : flags) {
            int index = flagMappings.get(flag);
            if (index >= 0) {
                value |= 1 << index;
            }
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MetadataFlags)) return false;
        MetadataFlags that = (MetadataFlags) o;
        return Objects.equals(this.flags, that.flags);
    }

    @Override
    public int hashCode() {
        return flags.hashCode();
    }
}
