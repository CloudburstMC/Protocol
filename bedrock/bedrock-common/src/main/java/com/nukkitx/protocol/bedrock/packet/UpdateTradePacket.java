package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UpdateTradePacket extends BedrockPacket {
    protected short windowId;
    protected short windowType;
    protected int unknown0; // Couldn't find anything on this one.
    protected int unknown1; // Something to do with AI and randomness?
    protected boolean willing;
    protected long traderUniqueEntityId;
    protected long playerUniqueEntityId;
    protected String displayName;
    protected Tag<?> offers;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
