package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.Value;

import java.util.List;
import java.util.StringJoiner;

@Value
@Deprecated
public class ItemStackActionDeprecated {
    private final byte type;
    private final boolean bool0;
    private final byte byte0;
    private final int varInt0;
    private final int varInt1;
    private final byte baseByte0;
    private final byte baseByte1;
    private final byte baseByte2;
    private final int baseVarInt0;
    private final byte flagsByte0;
    private final byte flagsByte1;
    private final int flagsVarInt0;
    private final List<ItemData> items;

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("type=" + type);

        switch (type) {
            case 0:
            case 1:
            case 2:
                joiner.add("baseByte0=" + baseByte0)
                        .add("baseByte1=" + baseByte1)
                        .add("baseByte2=" + baseByte2)
                        .add("baseVarInt0=" + baseVarInt0)
                        .add("flagsByte0=" + flagsByte0)
                        .add("flagsByte1=" + flagsByte1)
                        .add("flagsVarInt0=" + flagsVarInt0);
                break;
            case 3:
                joiner.add("bool0=" + bool0)
                        .add("baseByte0=" + baseByte0)
                        .add("baseByte1=" + baseByte1)
                        .add("baseByte2=" + baseByte2)
                        .add("baseVarInt0=" + baseVarInt0);
                break;
            case 4:
            case 5:
                joiner.add("baseByte0=" + baseByte0)
                        .add("baseByte1=" + baseByte1)
                        .add("baseByte2=" + baseByte2)
                        .add("baseVarInt0=" + baseVarInt0);
                break;
            case 6:
                joiner.add("byte0=" + byte0);
                break;
            case 8:
                joiner.add("varInt0=" + varInt0)
                        .add("varInt1=" + varInt1);
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                joiner.add("varInt0=" + varInt0);
                break;
            case 17:
                joiner.add("items=" + items);
                break;
        }
        return "ItemStackAction(" + joiner.toString() + ")";
    }
}
