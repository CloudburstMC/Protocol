package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.network.util.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {
    private static final String[] EMPTY = new String[0];
    public static final Item AIR = new Item(0, (short) 0, 0, null, EMPTY, EMPTY);

    private final int id;
    private final short damage;
    private final int count;
    private final CompoundTag tag;
    private final String[] canPlace;
    private final String[] canBreak;

    public static Item of(int id, short damage, int count) {
        return of(id, damage, count, null);
    }

    public static Item of(int id, short damage, int count, CompoundTag tag) {
        return of(id, damage, count, tag, EMPTY, EMPTY);
    }

    public static Item of(int id, short damage, int count, CompoundTag tag, String[] canPlace, String[] canBreak) {
        if (id == 0) {
            return AIR;
        }
        Preconditions.checkNotNull(canPlace, "canPlace");
        Preconditions.checkNotNull(canBreak, "canBreak");
        Preconditions.checkArgument(count < 256, "count exceeds maximum of 255");
        return new Item(id, damage, count, tag, canPlace, canBreak);
    }
}
