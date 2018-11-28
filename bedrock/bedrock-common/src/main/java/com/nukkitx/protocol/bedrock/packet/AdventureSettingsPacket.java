package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdventureSettingsPacket extends BedrockPacket {
    protected int playerFlags;
    protected int commandPermission;
    protected int worldFlags;
    protected int playerPermission;
    protected int customFlags;
    protected long uniqueEntityId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
