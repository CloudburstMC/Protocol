package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientCacheBlobStatusPacket extends BedrockPacket {
    private final TLongList acks = new TLongArrayList();
    private final TLongList naks = new TLongArrayList();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CACHE_BLOB_STATUS;
    }
}
