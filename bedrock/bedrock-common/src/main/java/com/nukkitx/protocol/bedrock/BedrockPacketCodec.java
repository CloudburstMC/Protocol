package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import com.nukkitx.protocol.bedrock.packet.UnknownPacket;
import com.nukkitx.protocol.bedrock.util.TIntHashBiMap;
import com.nukkitx.protocol.serializer.PacketSerializer;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
@RequiredArgsConstructor
public final class BedrockPacketCodec {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPacketCodec.class);

    @Getter
    private final int protocolVersion;
    private final PacketSerializer<BedrockPacket>[] serializers;
    private final TIntHashBiMap<Class<BedrockPacket>> idBiMap;
    private final PacketSerializer<PacketHeader> headerSerializer;

    public static Builder builder() {
        return new Builder();
    }

    public BedrockPacket tryDecode(ByteBuf buf) {
        PacketHeader header = new PacketHeader();

        headerSerializer.deserialize(buf, header);

        BedrockPacket packet;
        try {
            packet = idBiMap.get(header.getPacketId()).newInstance();
        } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        packet.setHeader(header);

        serializers[header.getPacketId()].deserialize(buf, packet);

        if (log.isDebugEnabled() && buf.isReadable()) {
            log.debug(packet.getClass().getSimpleName() + " still has " + buf.readableBytes() + " bytes to read!");
        }
        return packet;
    }

    public ByteBuf tryEncode(BedrockPacket packet) {
        PacketHeader header = packet.getHeader();
        if (header == null) {
            header = new PacketHeader();
        }
        int packetId = getId(packet);
        header.setPacketId(packetId);

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        headerSerializer.serialize(buf, header);
        serializers[packetId].serialize(buf, packet);
        return buf;
    }

    @SuppressWarnings("unchecked")
    public int getId(BedrockPacket packet) {
        Class<BedrockPacket> clazz = (Class<BedrockPacket>) packet.getClass();
        int id = idBiMap.get(clazz);
        if (id == -1) {
            throw new IllegalArgumentException("Packet ID for " + clazz.getName() + " does not exist.");
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final TIntObjectMap<PacketSerializer<BedrockPacket>> serializers = new TIntObjectHashMap<>();
        private final TIntHashBiMap<Class<BedrockPacket>> idBiMap = new TIntHashBiMap<>((Class) UnknownPacket.class);
        private int protocolVersion = -1;
        private PacketSerializer<PacketHeader> headerSerializer = null;

        public <T extends BedrockPacket> Builder registerPacket(Class<T> packetClass, PacketSerializer<T> packetSerializer, @Nonnegative int id) {
            Preconditions.checkArgument(id >= 0, "id cannot be negative");
            Class<BedrockPacket> clazz = (Class<BedrockPacket>) packetClass;
            PacketSerializer<BedrockPacket> serializer = (PacketSerializer<BedrockPacket>) packetSerializer;
            Preconditions.checkArgument(!idBiMap.containsKey(id), "Packet id already registered");
            Preconditions.checkArgument(!idBiMap.containsValue(clazz), "Packet class already registered");

            serializers.put(id, serializer);
            idBiMap.put(id, clazz);
            return this;
        }

        public Builder protocolVersion(@Nonnegative int protocolVersion) {
            Preconditions.checkArgument(protocolVersion >= 0, "protocolVersion cannot be negative");
            this.protocolVersion = protocolVersion;
            return this;
        }

        public Builder headerSerializer(@Nonnull PacketSerializer<PacketHeader> headerSerializer) {
            Preconditions.checkNotNull(headerSerializer, "headerFactory");
            this.headerSerializer = headerSerializer;
            return this;
        }

        public BedrockPacketCodec build() {
            Preconditions.checkArgument(protocolVersion < 0, "No protocol version defined");
            Preconditions.checkNotNull(headerSerializer, "headerSerializer cannot be null");
            int largestId = -1;
            for (int id : serializers.keys()) {
                if (id > largestId) {
                    largestId = id;
                }
            }
            Preconditions.checkArgument(largestId > -1, "Must have at least one packet registered");
            PacketSerializer<BedrockPacket>[] serializers = new PacketSerializer[largestId + 1];

            TIntObjectIterator<PacketSerializer<BedrockPacket>> iterator = this.serializers.iterator();

            while (iterator.hasNext()) {
                iterator.advance();
                serializers[iterator.key()] = iterator.value();
            }
            return new BedrockPacketCodec(protocolVersion, serializers, idBiMap, headerSerializer);
        }
    }
}
