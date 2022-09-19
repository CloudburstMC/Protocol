package org.cloudburstmc.protocol.bedrock.data.inventory;

import com.nukkitx.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;

/**
 * Represents item data that is sent over the network.
 */
public interface ItemData {
    ItemData AIR = new BaseItemData(ItemDefinition.AIR, 0, 0, null, BaseItemData.EMPTY_ARRAY, BaseItemData.EMPTY_ARRAY, 0, 0, false, 0);

    /**
     * Gets the {@link ItemDefinition}.
     *
     * @return the item definition
     */
    ItemDefinition getDefinition();

    /**
     * Gets the item damage.
     *
     * @return the item damage
     */
    int getDamage();

    /**
     * Gets the item count.
     *
     * @return the item count
     */
    int getCount();

    /**
     * Gets the item NBT.
     *
     * @return the item NBT
     */
    NbtMap getTag();

    /**
     * Gets the blocks this item can be placed on.
     *
     * @return the blocks this item can be placed on
     */
    String[] getCanPlace();

    /**
     * Gets the blocks this item can break
     *
     * @return the blocks this item can break
     */
    String[] getCanBreak();

    /**
     * Gets the item's blocking ticks.
     *
     * @return the item's blocking ticks
     */
    long getBlockingTicks();

    /**
     * Gets the block runtime id of this item,
     * if applicable.
     *
     * @return the block runtime id of this item
     */
    int getBlockRuntimeId();

    /**
     * Gets whether this item is using a net id.
     *
     * @return whether this item is using a net id
     */
    boolean isUsingNetId();

    /**
     * Gets the item's net id.
     *
     * @return the item's net id
     */
    int getNetId();

    /**
     * Sets the item's net id.
     *
     * @param netId the item's net id
     */
    void setNetId(int netId);

    /**
     * Gets if this item is valid.
     *
     * @return if this item is valid
     */
    boolean isValid();

    /**
     * Gets if this item is null.
     *
     * @return if this item is null
     */
    boolean isNull();

    /**
     * Checks if this item us equal to another {@link ItemData}.
     * @param other the item data to compare against
     * @param checkAmount whether to check the amount
     * @param checkMetadata whether to check the metadata
     * @param checkUserdata whether to check the user data
     * @return if this item is equal to the other item data
     */
    boolean equals(ItemData other, boolean checkAmount, boolean checkMetadata, boolean checkUserdata);

    /**
     * Creates a new builder for this item.
     *
     * @return a new builder for this item
     */
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new builder for this ItemData.
     *
     * @return a new builder for this ItemData
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a builder for this item data.
     */
    class Builder {
        private ItemDefinition definition;
        private int damage;
        private int count;
        private NbtMap tag;
        private String[] canPlace;
        private String[] canBreak;
        private long blockingTicks;
        private int blockRuntimeId;
        private boolean usingNetId;
        private int netId;

        private Builder() {
        }

        private Builder(ItemData data) {
            this.definition = data.getDefinition();
            this.damage = data.getDamage();
            this.count = data.getCount();
            this.tag = data.getTag();
            this.canPlace = data.getCanPlace();
            this.canBreak = data.getCanBreak();
            this.blockingTicks = data.getBlockingTicks();
            this.blockRuntimeId = data.getBlockRuntimeId();
            this.usingNetId = data.isUsingNetId();
            this.netId = data.getNetId();
        }

        public Builder definition(ItemDefinition definition) {
            this.definition = definition;
            return this;
        }

        public Builder damage(int damage) {
            this.damage = damage;
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder tag(NbtMap tag) {
            this.tag = tag;
            return this;
        }

        public Builder canPlace(String... canPlace) {
            this.canPlace = canPlace;
            return this;
        }

        public Builder canBreak(String... canBreak) {
            this.canBreak = canBreak;
            return this;
        }

        public Builder blockingTicks(long blockingTicks) {
            this.blockingTicks = blockingTicks;
            return this;
        }

        public Builder blockRuntimeId(int blockRuntimeId) {
            this.blockRuntimeId = blockRuntimeId;
            return this;
        }

        public Builder usingNetId(boolean usingNetId) {
            this.usingNetId = usingNetId;
            return this;
        }

        public Builder netId(int netId) {
            this.netId = netId;
            return this;
        }

        public ItemData build() {
            return new BaseItemData(definition, damage, count, tag, canPlace, canBreak, blockingTicks, blockRuntimeId, usingNetId, netId);
        }
    }
}
