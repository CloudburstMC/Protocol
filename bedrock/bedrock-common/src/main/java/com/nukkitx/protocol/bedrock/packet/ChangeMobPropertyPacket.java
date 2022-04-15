package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Server-bound packet to change the properties of a mob.
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class ChangeMobPropertyPacket extends BedrockPacket {
    private long uniqueEntityId;
    private String property;
    private boolean boolValue;
    private String stringValue;
    private int intValue;
    private float floatValue;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CHANGE_MOB_PROPERTY;
    }
}