package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.exception.PacketSerializeException;
import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import com.nukkitx.protocol.bedrock.packet.UnknownPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
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
    @Getter
    private final String minecraftVersion;
    private final PacketSerializer<BedrockPacket>[] serializers;
    private final Int2ObjectBiMap<Class<? extends BedrockPacket>> idBiMap;
    private final PacketSerializer<PacketHeader> headerSerializer;

    public static Builder builder() {
        return new Builder();
    }

    public BedrockPacket tryDecode(ByteBuf buf) throws PacketSerializeException {
        PacketHeader header = new PacketHeader();

        headerSerializer.deserialize(buf, header);

        BedrockPacket packet;
        try {
            packet = idBiMap.get(header.getPacketId()).newInstance();
        } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        packet.setHeader(header);

        try {
            if (packet instanceof UnknownPacket) {
                ((UnknownPacket) packet).deserialize(buf, (UnknownPacket) packet);
            } else {
                serializers[header.getPacketId()].deserialize(buf, packet);
            }
        } catch (Exception e) {
            throw new PacketSerializeException("Error whilst deserializing " + packet, e);
        }

        if (log.isDebugEnabled() && buf.isReadable()) {
            log.debug(packet.getClass().getSimpleName() + " still has " + buf.readableBytes() + " bytes to read!");
        }
        return packet;
    }

    @SuppressWarnings("unchecked")
    public ByteBuf tryEncode(BedrockPacket packet) throws PacketSerializeException {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        try {
            PacketHeader header = packet.getHeader();
            if (header == null) {
                header = new PacketHeader();
            }

            PacketSerializer<BedrockPacket> serializer;
            if (packet instanceof UnknownPacket) {
                serializer = (PacketSerializer<BedrockPacket>) packet;
            } else {
                int packetId = getId(packet.getClass());
                header.setPacketId(packetId);
                serializer = serializers[packetId];
            }
            headerSerializer.serialize(buf, header);
            serializer.serialize(buf, packet);
        } catch (Exception e) {
            buf.release();
            throw new PacketSerializeException("Error whilst serializing " + packet, e);
        } finally {
            ReferenceCountUtil.release(packet);
        }
        return buf;
    }

    public int getId(Class<? extends BedrockPacket> clazz) {
        int id = idBiMap.get(clazz);
        if (id == -1) {
            throw new IllegalArgumentException("Packet ID for " + clazz.getName() + " does not exist.");
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final Int2ObjectMap<PacketSerializer<BedrockPacket>> serializers = new Int2ObjectOpenHashMap<>();
        private final Int2ObjectBiMap<Class<? extends BedrockPacket>> idBiMap = new Int2ObjectBiMap<>(UnknownPacket.class);
        private int protocolVersion = -1;
        private String minecraftVersion = null;
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

        public Builder minecraftVersion(@Nonnull String minecraftVersion) {
            Preconditions.checkNotNull(minecraftVersion, "minecraftVersion");
            Preconditions.checkArgument(!minecraftVersion.isEmpty() && minecraftVersion.split("\\.").length > 2, "Invalid minecraftVersion");
            this.minecraftVersion = minecraftVersion;
            return this;
        }

        public Builder headerSerializer(@Nonnull PacketSerializer<PacketHeader> headerSerializer) {
            Preconditions.checkNotNull(headerSerializer, "headerFactory");
            this.headerSerializer = headerSerializer;
            return this;
        }

        public BedrockPacketCodec build() {
            Preconditions.checkArgument(protocolVersion >= 0, "No protocol version defined");
            Preconditions.checkNotNull(minecraftVersion, "No Minecraft version defined");
            Preconditions.checkNotNull(headerSerializer, "headerSerializer cannot be null");
            int largestId = -1;
            for (int id : serializers.keySet()) {
                if (id > largestId) {
                    largestId = id;
                }
            }
            Preconditions.checkArgument(largestId > -1, "Must have at least one packet registered");
            PacketSerializer<BedrockPacket>[] serializers = new PacketSerializer[largestId + 1];

            for (Int2ObjectMap.Entry<PacketSerializer<BedrockPacket>> entry : this.serializers.int2ObjectEntrySet()) {
                serializers[entry.getIntKey()] = entry.getValue();
            }
            return new BedrockPacketCodec(protocolVersion, minecraftVersion, serializers, idBiMap, headerSerializer);
        }
    }
}
