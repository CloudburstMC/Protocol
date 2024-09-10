package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class ClientCacheMissResponsePacket extends AbstractReferenceCounted implements BedrockPacket {
    private final Long2ObjectMap<ByteBuf> blobs = new Long2ObjectLinkedOpenHashMap<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CACHE_MISS_RESPONSE;
    }

    @Override
    protected void deallocate() {
        this.blobs.values().forEach(ReferenceCounted::release);
    }

    @Override
    public ClientCacheMissResponsePacket touch(Object hint) {
        this.blobs.values().forEach(byteBuf -> byteBuf.touch(hint));
        return this;
    }

    @Override
    public ClientCacheMissResponsePacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}

