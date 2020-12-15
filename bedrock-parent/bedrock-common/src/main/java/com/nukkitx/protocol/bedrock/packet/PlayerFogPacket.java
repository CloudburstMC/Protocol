package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Tracks the current fog effects applied to a client
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerFogPacket extends BedrockPacket {

    /**
     * Fog stack containing fog effects from the /fog command
     *
     * @param fogStack list of fog effects
     * @return list of fog effects
     */
    private final List<String> fogStack = new ObjectArrayList<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_FOG;
    }
}
