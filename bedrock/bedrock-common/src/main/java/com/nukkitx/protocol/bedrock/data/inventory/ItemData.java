package com.nukkitx.protocol.bedrock.data.inventory;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.util.Preconditions;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.NonFinal;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Objects;

@Data
@Immutable
@Builder(toBuilder = true, builderClassName = "Builder")
public final class ItemData {
    private static final String[] EMPTY = new String[0];
    public static final ItemData AIR = new ItemData(0, 0, 0, null, EMPTY, EMPTY, 0, 0, false, 0);

    @NonFinal
    private int id;
    private final int damage;
    private final int count;
    private final NbtMap tag;
    private final String[] canPlace;
    private final String[] canBreak;
    private final long blockingTicks;
    private final int blockRuntimeId;
    @NonFinal
    private boolean usingNetId;
    private int netId;

    private ItemData(int id, int damage, int count, NbtMap tag, String[] canPlace, String[] canBreak, long blockingTicks, int blockRuntimeId, boolean hasNetId, int netId) {
        Preconditions.checkArgument(count < 256, "count exceeds maximum of 255");
        this.id = id;
        this.damage = damage;
        this.count = count;
        this.tag = tag;
        this.canPlace = canPlace == null ? EMPTY : canPlace;
        this.canBreak = canBreak == null ? EMPTY : canBreak;
        this.blockingTicks = blockingTicks;
        this.blockRuntimeId = blockRuntimeId;
        this.netId = netId;
    }

    public boolean isValid() {
        return !isNull() && id != 0;
    }

    public boolean isNull() {
        return count <= 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, damage, count, tag, Arrays.hashCode(canPlace), Arrays.hashCode(canBreak), blockingTicks,
                blockRuntimeId);
    }

    public boolean equals(ItemData other, boolean checkAmount, boolean checkMetadata, boolean checkUserdata) {
        return id == other.id &&
                (!checkAmount || count == other.count) &&
                (!checkMetadata || (damage == other.damage && blockRuntimeId == other.blockRuntimeId)) &&
                (!checkUserdata || (Objects.equals(tag, other.tag) && Arrays.equals(canPlace, other.canPlace) && Arrays.equals(canBreak, other.canBreak)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ItemData)) return false;
        return equals((ItemData) obj, true, true, true);
    }
}
