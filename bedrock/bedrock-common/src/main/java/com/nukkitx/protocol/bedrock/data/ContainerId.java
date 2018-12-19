package com.nukkitx.protocol.bedrock.data;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum ContainerId {
    DROP_CONTENTS(-100),

    BEACON(-24),
    TRADING_OUTPUT(-23),
    TRADING_USE_INPUTS(-22),
    TRADING_INPUT_2(-21),
    TRADING_INPUT_1(-20),

    ENCHANT_OUTPUT(-17),
    ENCHANT_MATERIAL(-16),
    ENCHANT_INPUT(-15),

    ANVIL_OUTPUT(-13),
    ANVIL_RESULT(-12),
    ANVIL_MATERIAL(-11),
    CONTAINER_INPUT(-10),

    CRAFTING_USE_INGREDIENT(-5),
    CRAFTING_RESULT(-4),
    CRAFTING_REMOVE_INGREDIENT(-3),
    CRAFTING_ADD_INGREDIENT(-2),
    NONE(-1),
    INVENTORY(0),
    FIRST(1),

    LAST(100),

    OFFHAND(119),
    ARMOR(120),
    CREATIVE(121),
    HUD(122),
    FIXED_INVENTORY(123),
    CURSOR(124);

    private static final TIntObjectMap<ContainerId> BY_ID = new TIntObjectHashMap<>();

    static {
        for (ContainerId id : values()) {
            BY_ID.put(id.id, id);
        }
    }

    private final int id;

    ContainerId(int id) {
        this.id = id;
    }

    public static ContainerId byId(int id) {
        return BY_ID.get(id);
    }

    public int id() {
        return id;
    }
}
