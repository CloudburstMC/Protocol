package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.PacketCodec;
import com.nukkitx.network.PacketFactory;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.function.Supplier;

@Immutable
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public final class BedrockPacketCodec implements PacketCodec<BedrockPacket> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPacketCodec.class);

    private final TIntSet compatibleVersions;
    private final PacketFactory<BedrockPacket>[] factories;
    private final TObjectIntMap<Class<? extends BedrockPacket>> idMapping;
    private final Supplier<PacketHeader> headerFactory;

    public static Builder builder() {
        return new Builder();
    }

    public final <T extends BedrockPacket> T createPacket(@Nonnull Class<T> clazz) {
        int id = idMapping.get(clazz);
        Preconditions.checkArgument(id >= 0 && id < factories.length, "Invalid packet");
        T packet = (T) factories[id].newInstance();
        PacketHeader header = headerFactory.get();
        header.setPacketId(id);
        packet.setHeader(header);

        return packet;
    }

    @Override
    public BedrockPacket tryDecode(ByteBuf buf) {
        PacketHeader header = headerFactory.get();
        header.decode(buf);

        BedrockPacket packet = factories[header.getPacketId()].newInstance();
        packet.setHeader(header);
        packet.decode(buf);

        if (log.isDebugEnabled() && buf.isReadable()) {
            log.debug(packet.getClass().getSimpleName() + " still has " + buf.readableBytes() + " bytes to read!");
        }
        return packet;
    }

    @Override
    public ByteBuf tryEncode(BedrockPacket packet) {
        Preconditions.checkNotNull(packet.getHeader(), "packet header null");
        packet.getHeader().setPacketId(getId(packet));

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        packet.getHeader().encode(buf);
        packet.encode(buf);
        return buf;
    }

    @Override
    public int getId(BedrockPacket packet) {
        Class<? extends BedrockPacket> clazz = packet.getClass();
        int id = idMapping.get(clazz);
        if (id == -1) {
            throw new IllegalArgumentException("Packet ID for " + clazz.getName() + " does not exist.");
        }
        return id;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final TIntObjectMap<PacketFactory<BedrockPacket>> packets = new TIntObjectHashMap<>();
        private final TObjectIntMap<Class<? extends BedrockPacket>> ids = new TObjectIntHashMap<>(64, 0.75f, -1);
        private final TIntSet compatibleVersions = new TIntHashSet();
        private Supplier<PacketHeader> headerFactory = null;

        public <T extends BedrockPacket> Builder registerPacket(Class<T> clazz, PacketFactory<? extends T> packet, @Nonnegative int id) {
            Preconditions.checkArgument(id >= 0, "id cannot be negative");
            if (packets.containsKey(id)) {
                throw new IllegalArgumentException("Packet id already registered");
            }
            if (ids.containsKey(clazz)) {
                throw new IllegalArgumentException("Packet class already registered");
            }

            packets.put(id, (PacketFactory<BedrockPacket>) packet);
            ids.put(clazz, id);
            return this;
        }

        public Builder addCompatibleVersion(@Nonnegative int protocolVersion) {
            Preconditions.checkArgument(protocolVersion >= 0, "protocolVersion cannot be negative");
            compatibleVersions.add(protocolVersion);
            return this;
        }

        public Builder packetHeader(@Nonnull Supplier<PacketHeader> headerFactory) {
            Preconditions.checkNotNull(headerFactory, "headerFactory");
            this.headerFactory = headerFactory;
            return this;
        }

        public BedrockPacketCodec build() {
            Preconditions.checkArgument(!compatibleVersions.isEmpty(), "Must have at least one compatible version");
            Preconditions.checkNotNull(headerFactory, "headerFactory cannot be null");
            int largestId = -1;
            for (int id : packets.keys()) {
                if (id > largestId) {
                    largestId = id;
                }
            }
            Preconditions.checkArgument(largestId > -1, "Must have at least one packet registered");
            PacketFactory<BedrockPacket>[] packets = new PacketFactory[largestId + 1];

            TIntObjectIterator<PacketFactory<BedrockPacket>> iterator = this.packets.iterator();

            while (iterator.hasNext()) {
                iterator.advance();
                packets[iterator.key()] = iterator.value();
            }
            return new BedrockPacketCodec(compatibleVersions, packets, ids, headerFactory);
        }
    }
}
