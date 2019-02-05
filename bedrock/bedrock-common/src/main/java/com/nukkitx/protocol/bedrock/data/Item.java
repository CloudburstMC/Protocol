package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.network.util.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Arrays;
import java.util.Objects;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Item {
    private static final String[] EMPTY = new String[0];
    public static final Item AIR = new Item(0, (short) 0, 0, null, EMPTY, EMPTY);

    private final int id;
    private final short damage;
    private final int count;
    private final Tag<?> tag;
    private final String[] canPlace;
    private final String[] canBreak;

    public static Item of(int id, short damage, int count) {
        return of(id, damage, count, null);
    }

    public static Item of(int id, short damage, int count, Tag<?> tag) {
        return of(id, damage, count, tag, EMPTY, EMPTY);
    }

    public static Item of(int id, short damage, int count, Tag<?> tag, String[] canPlace, String[] canBreak) {
        if (id == 0) {
            return AIR;
        }
        Preconditions.checkNotNull(canPlace, "canPlace");
        Preconditions.checkNotNull(canBreak, "canBreak");
        Preconditions.checkArgument(count < 256, "count exceeds maximum of 255");
        return new Item(id, damage, count, tag, canPlace, canBreak);
    }

    public boolean isValid() {
        return !isNull() && id != 0;
    }

    public boolean isNull() {
        return count <= 0;
    }

    public boolean equals(Item other, boolean checkAmount, boolean checkMetadata, boolean checkUserdata) {
        return (!checkAmount || count == other.count) &&
                (!checkMetadata || damage == other.damage) &&
                (!checkUserdata || (Objects.equals(tag, other.tag) && Arrays.equals(canPlace, other.canPlace) && Arrays.equals(canBreak, other.canBreak)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Item)) return false;
        return equals((Item) obj, true, true, true);
    }
}
