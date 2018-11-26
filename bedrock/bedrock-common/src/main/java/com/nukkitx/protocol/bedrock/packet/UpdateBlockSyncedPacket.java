package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UpdateBlockSyncedPacket extends UpdateBlockPacket {
    protected long runtimeEntityId;
    protected long unknownLong1;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
