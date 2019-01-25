package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.util.TIntHashBiMap;
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

    public static MetadataFlags create(long value, int index, TIntHashBiMap<Metadata.Flag> flagMappings) {
        MetadataFlags flags = new MetadataFlags();
        for (int i = index * 64; i < index + 64; i++) {
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
    public boolean setFlag(@Nonnull Metadata.Flag flag, boolean value) {
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
    public boolean getFlag(@Nonnull Metadata.Flag flag) {
        Preconditions.checkNotNull(flag, "flag");
        return flags.contains(flag);
    }

    public long get(int index, TIntHashBiMap<Metadata.Flag> flagMappings) {
        long value = 0;
        final long lower = index * 64;
        final long upper = lower + 64;
        for (Metadata.Flag flag : flags) {
            int flagIndex = flagMappings.get(flag);
            if (flagIndex >= lower && flagIndex < upper) {
                value |= 1 << (flagIndex & 0x3f);
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

    public void merge(MetadataFlags flags) {
        this.flags.addAll(flags.flags);
    }

    public MetadataFlags copy() {
        MetadataFlags flags = new MetadataFlags();
        flags.flags.addAll(this.flags);
        return flags;
    }
}
