package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ClientCacheBlobStatusPacket extends BedrockPacket {
    private final LongList acks = new LongArrayList();
    private final LongList naks = new LongArrayList();

    public ClientCacheBlobStatusPacket addAck(long ack) {
        this.acks.add(ack);
        return this;
    }

    public ClientCacheBlobStatusPacket addAcks(long... acks) {
        for (long ack : acks) {
            this.acks.add(ack);
        }
        return this;
    }

    public ClientCacheBlobStatusPacket addNak(long nak) {
        this.naks.add(nak);
        return this;
    }

    public ClientCacheBlobStatusPacket addNaks(long... naks) {
        for (long nak : naks) {
            this.naks.add(nak);
        }
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CACHE_BLOB_STATUS;
    }
}
