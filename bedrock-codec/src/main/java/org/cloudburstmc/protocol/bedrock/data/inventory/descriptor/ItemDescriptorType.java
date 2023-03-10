package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

public enum ItemDescriptorType {
    INVALID,
    DEFAULT,
    MOLANG,
    ITEM_TAG,
    DEFERRED,
    /**
     * @since v575
     */
    COMPLEX_ALIAS
}