package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.PlayerArmorDamageSerializer_v407;

public class PlayerArmorDamageSerializer_v712 extends PlayerArmorDamageSerializer_v407 {
    public static final PlayerArmorDamageSerializer_v712 INSTANCE = new PlayerArmorDamageSerializer_v712();

    @Override
    protected int getMaxFlagIndex() {
        return 4;
    }
}