package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTradePacket extends BedrockPacket {
    private short windowId;
    private short windowType;
    private int unknownInt; // Hardcoded to 0
    private int tradeTier;
    private long traderUniqueEntityId;
    private long playerUniqueEntityId;
    private String displayName;
    private Tag<?> offers;
    private boolean screen2;
    private boolean willing;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
