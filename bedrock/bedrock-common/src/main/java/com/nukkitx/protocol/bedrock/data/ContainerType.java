package com.nukkitx.protocol.bedrock.data;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum ContainerType {
    UNTRACKED_UI_INTERACTION(-9),
    INVENTORY(-1),
    CONTAINER(0),
    WORKBENCH(1),
    FURNACE(2),
    ENCHANTMENT(3),
    BREWING_STAND(4),
    ANVIL(5),
    DISPENSER(6),
    DROPPER(7),
    HOPPER(8),
    CAULDRON(9),
    CHEST(10),
    MINECART_HOPPER(11),
    HORSE(12),
    BEACON(13),
    STRUCTURE_EDITOR(14),
    TRADING(15),
    COMMAND_BLOCK(16),
    JUKEBOX(17),
    COMPOUND_CREATOR(20),
    ELEMENT_CONSTRUCTOR(21),
    MATERIAL_REDUCER(22),
    LAB_TABLE(23),
    GRINDSTONE(26),
    BLAST_FURNACE(27),
    SMOKER(28);

    public static final Int2ObjectMap<ContainerType> BY_ID = new Int2ObjectOpenHashMap<>();

    static {
        for (ContainerType type : values()) {
            BY_ID.put(type.id, type);
        }
    }

    private final int id;

    ContainerType(final int id) {
        this.id = id;
    }

    public static ContainerType fromId(int id) {
        return BY_ID.get(id);
    }

    public int id() {
        return id;
    }
}
