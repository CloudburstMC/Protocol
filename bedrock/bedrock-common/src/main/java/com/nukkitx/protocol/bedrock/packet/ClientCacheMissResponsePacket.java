package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import lombok.Data;

import java.util.Arrays;

@Data
public class ClientCacheMissResponsePacket extends BedrockPacket {
    private final Long2ObjectMap<byte[]> blobs = new Long2ObjectOpenHashMap<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CACHE_MISS_RESPONSE;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ClientCacheMissResponsePacket)) return false;
        final ClientCacheMissResponsePacket other = (ClientCacheMissResponsePacket) o;

        ObjectIterator<Long2ObjectMap.Entry<byte[]>> thisIterator = this.blobs.long2ObjectEntrySet().iterator();

        for (Long2ObjectMap.Entry<byte[]> thatEntry : other.blobs.long2ObjectEntrySet()) {
            if (!thisIterator.hasNext()) {
                return false;
            }
            Long2ObjectMap.Entry<byte[]> thisEntry = thisIterator.next();
            if (thisEntry.getLongKey() != thatEntry.getLongKey() ||
                    !Arrays.equals(thisEntry.getValue(), thatEntry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        for (Long2ObjectMap.Entry<byte[]> entry : this.blobs.long2ObjectEntrySet()) {
            result = result * PRIME + Long.hashCode(entry.getLongKey());
            result = result * PRIME + Arrays.hashCode(entry.getValue());
        }
        return result;
    }
}
