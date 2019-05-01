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
public class EntityFlags {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityFlags.class);

    private final Set<EntityFlag> flags = new HashSet<>();

    public static EntityFlags create(long value, int index, TIntHashBiMap<EntityFlag> flagMappings) {
        EntityFlags flags = new EntityFlags();
        final int lower = index * 64;
        final int upper = lower + 64;
        for (int i = lower; i < upper; i++) {
            int idx = i & 0x3f;
            if ((value & (1L << idx)) != 0) {
                EntityFlag flag = flagMappings.get(i);
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
     * Set {@link EntityFlag} value
     *
     * @param flag  flag to be set
     * @param value value to be set to
     * @return whether there was a change
     */
    public boolean setFlag(@Nonnull EntityFlag flag, boolean value) {
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
     * Get {@link EntityFlag} value
     *
     * @param flag flag to get
     * @return value of flag
     */
    public boolean getFlag(@Nonnull EntityFlag flag) {
        Preconditions.checkNotNull(flag, "flag");
        return flags.contains(flag);
    }

    public long get(int index, TIntHashBiMap<EntityFlag> flagMappings) {
        long value = 0;
        final int lower = index * 64;
        final int upper = lower + 64;
        for (EntityFlag flag : flags) {
            int flagIndex = flagMappings.get(flag);
            if (flagIndex >= lower && flagIndex < upper) {
                value |= 1L << (flagIndex & 0x3f);
            }
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EntityFlags)) return false;
        EntityFlags that = (EntityFlags) o;
        return Objects.equals(this.flags, that.flags);
    }

    @Override
    public int hashCode() {
        return flags.hashCode();
    }

    public void merge(EntityFlags flags) {
        this.flags.addAll(flags.flags);
    }

    public EntityFlags copy() {
        EntityFlags flags = new EntityFlags();
        flags.flags.addAll(this.flags);
        return flags;
    }
}
