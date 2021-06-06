package org.cloudburstmc.protocol.bedrock.data.inventory;

import com.nukkitx.nbt.NbtMap;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.NonFinal;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Objects;

@Data
@Immutable
@Builder(toBuilder = true, builderClassName = "Builder")
public final class ItemData {
    private static final String[] EMPTY = new String[0];
    public static final ItemData AIR = new ItemData(ItemDefinition.AIR, 0, 0, null, EMPTY, EMPTY, 0, 0, false, 0);

    private ItemDefinition definition;
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

    private ItemData(ItemDefinition definition, int damage, int count, NbtMap tag, String[] canPlace, String[] canBreak, long blockingTicks, int blockRuntimeId, boolean hasNetId, int netId) {
        this.definition = definition;
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
        return !isNull() && definition != null && definition != ItemDefinition.AIR;
    }

    public boolean isNull() {
        return count <= 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition, damage, count, tag, Arrays.hashCode(canPlace), Arrays.hashCode(canBreak), blockingTicks,
                blockRuntimeId);
    }

    public boolean equals(ItemData other, boolean checkAmount, boolean checkMetadata, boolean checkUserdata) {
        return definition == other.definition &&
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
