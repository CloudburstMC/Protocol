package com.nukkitx.protocol.bedrock.data.entity;

import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.Set;

@ToString
public class EntityFlags {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityFlags.class);

    private final Set<EntityFlag> flags = EnumSet.noneOf(EntityFlag.class);

    /**
     * Set {@link EntityFlag} value
     *
     * @param flag  flag to be set
     * @param value value to be set to
     * @return whether there was a change
     */
    public boolean setFlag(@Nonnull EntityFlag flag, boolean value) {
        Preconditions.checkNotNull(flag, "flag");
        boolean oldValue = flags.contains(flag);

        if (oldValue != value) {
            if (value) {
                flags.add(flag);
            } else {
                flags.remove(flag);
            }
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

    public long get(int index, Int2ObjectBiMap<EntityFlag> flagMappings) {
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

    public void set(long value, int index, Int2ObjectBiMap<EntityFlag> flagMappings) {
        final int lower = index * 64;
        final int upper = lower + 64;
        for (int i = lower; i < upper; i++) {
            int idx = i & 0x3f;
            if ((value & (1L << idx)) != 0) {
                EntityFlag flag = flagMappings.get(i);
                if (flag != null) {
                    flags.add(flag);
                } else {
                    log.debug("Unknown entity flag index {} detected", i);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EntityFlags)) return false;
        EntityFlags that = (EntityFlags) o;
        return this.flags.equals(that.flags);
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
