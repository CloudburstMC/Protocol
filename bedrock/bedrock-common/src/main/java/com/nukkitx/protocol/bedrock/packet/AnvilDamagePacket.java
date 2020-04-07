package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AnvilDamagePacket extends BedrockPacket {
    private int damage;
    private Vector3i position;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANVIL_DAMAGE;
    }
}
