package org.cloudburstmc.protocol.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LongKeys {

    public static int high(long key) {
        return (int) (key >> 32);
    }

    public static int low(long key) {
        return (int) key;
    }

    public static long key(int high, int low) {
        return ((long) high << 32) | (low & 0xFFFFFFFFL);
    }
}
