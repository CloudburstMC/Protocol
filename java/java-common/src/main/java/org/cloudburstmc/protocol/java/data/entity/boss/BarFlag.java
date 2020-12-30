package org.cloudburstmc.protocol.java.data.entity.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BarFlag {
    DARKEN_SKY(0x01),
    END_MUSIC(0x02),
    CREATE_FOG(0x04);

    private static final BarFlag[] VALUES = values();

    private final int id;

    public static BarFlag getById(int id) {
        for (BarFlag flag : VALUES) {
            if (flag.id == id) {
                return flag;
            }
        }
        return null;
    }
}
