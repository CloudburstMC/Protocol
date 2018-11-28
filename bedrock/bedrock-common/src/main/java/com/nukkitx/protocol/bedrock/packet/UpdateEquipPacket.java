package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateEquipPacket extends BedrockPacket {
    protected short windowId;
    protected short windowType;
    protected int unknown0; // Couldn't find anything on this one. Looks like it isn't used?
    protected long uniqueEntityId;
    protected Tag<?> tag;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
