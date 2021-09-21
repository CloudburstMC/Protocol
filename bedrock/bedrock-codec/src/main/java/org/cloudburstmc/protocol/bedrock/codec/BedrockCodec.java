package org.cloudburstmc.protocol.bedrock.codec;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.UnknownPacket;
import org.lanternpowered.lmbda.LambdaFactory;
import org.lanternpowered.lmbda.MethodHandlesExtensions;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.cloudburstmc.protocol.common.util.Preconditions.*;

@Immutable
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BedrockCodec {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockCodec.class);

    @Getter
    private final int protocolVersion;
    @Getter
    private final String minecraftVersion;
    private final BedrockPacketDefinition<? extends BedrockPacket>[] packetsById;
    private final Map<Class<? extends BedrockPacket>, BedrockPacketDefinition<? extends BedrockPacket>> packetsByClass;
    private final Supplier<BedrockCodecHelper> helperFactory;
    @Getter
    private final int raknetProtocolVersion;

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public BedrockPacket tryDecode(BedrockCodecHelper helper, ByteBuf buf, int id) throws PacketSerializeException {
        BedrockPacketDefinition<? extends BedrockPacket> definition = getPacketDefinition(id);
        BedrockPacket packet;
        BedrockPacketSerializer<BedrockPacket> serializer;
        if (definition == null) {
            UnknownPacket unknownPacket = new UnknownPacket();
            unknownPacket.setPacketId(id);
            packet = unknownPacket;
            serializer = (BedrockPacketSerializer) unknownPacket;
        } else {
            packet = definition.getFactory().get();
            serializer = (BedrockPacketSerializer) definition.getSerializer();
        }

        try {
            serializer.deserialize(buf, helper, packet);
        } catch (Exception e) {
            throw new PacketSerializeException("Error whilst deserializing " + packet, e);
        }

        if (log.isDebugEnabled() && buf.isReadable()) {
            log.debug(packet.getClass().getSimpleName() + " still has " + buf.readableBytes() + " bytes to read!");
        }
        return packet;
    }

    @SuppressWarnings("unchecked")
    public <T extends BedrockPacket> void tryEncode(BedrockCodecHelper helper, ByteBuf buf, T packet) throws PacketSerializeException {
        try {
            BedrockPacketSerializer<T> serializer;
            if (packet instanceof UnknownPacket) {
                serializer = (BedrockPacketSerializer<T>) packet;
            } else {
                BedrockPacketDefinition<T> definition = getPacketDefinition((Class<T>) packet.getClass());
                serializer = definition.getSerializer();
            }
            serializer.serialize(buf, helper, packet);
        } catch (Exception e) {
            throw new PacketSerializeException("Error whilst serializing " + packet, e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BedrockPacket> BedrockPacketDefinition<T> getPacketDefinition(Class<T> packet) {
        checkNotNull(packet, "packet");
        return (BedrockPacketDefinition<T>) packetsByClass.get(packet);
    }

    public BedrockPacketDefinition<? extends BedrockPacket> getPacketDefinition(int id) {
        checkElementIndex(id, this.packetsById.length);
        return packetsById[id];
    }

    public BedrockCodecHelper createHelper() {
        return this.helperFactory.get();
    }

    public Builder toBuilder() {
        Builder builder = new Builder();

        builder.packets.putAll(this.packetsByClass);
        builder.protocolVersion = this.protocolVersion;
        builder.raknetProtocolVersion = this.raknetProtocolVersion;
        builder.minecraftVersion = this.minecraftVersion;
        builder.helperFactory = this.helperFactory;

        return builder;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final Map<Class<? extends BedrockPacket>, BedrockPacketDefinition<? extends BedrockPacket>> packets = new IdentityHashMap<>();
        private int protocolVersion = -1;
        private int raknetProtocolVersion = 10;
        private String minecraftVersion = null;
        private Supplier<BedrockCodecHelper> helperFactory;

        @SuppressWarnings("rawtypes")
        public <T extends BedrockPacket> Builder registerPacket(Class<T> packetClass, BedrockPacketSerializer<T> serializer, @Nonnegative int id) {
            checkArgument(id >= 0, "id cannot be negative");
            checkArgument(!packets.containsKey(packetClass), "Packet class already registered");

            Supplier<Object> factory;
            try {
                MethodHandles.Lookup lookup = MethodHandlesExtensions.privateLookupIn(packetClass, MethodHandles.lookup());
                MethodHandle handle = lookup.findConstructor(packetClass, MethodType.methodType(void.class));
                factory = LambdaFactory.createSupplier(handle);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalArgumentException("Unable to find suitable constructor for packet factory", e);
            }

            BedrockPacketDefinition<T> info = new BedrockPacketDefinition<>(id, (Supplier) factory, serializer);

            packets.put(packetClass, info);

            return this;
        }

        public <T extends BedrockPacket> Builder updateSerializer(Class<T> packetClass, BedrockPacketSerializer<T> serializer) {
            BedrockPacketDefinition<T> info = (BedrockPacketDefinition<T>) packets.get(packetClass);
            checkArgument(info != null, "Packet does not exist");
            BedrockPacketDefinition<T> updatedInfo = new BedrockPacketDefinition<>(info.getId(), info.getFactory(), serializer);

            packets.replace(packetClass, info, updatedInfo);

            return this;
        }

        public Builder deregisterPacket(Class<? extends BedrockPacket> packetClass) {
            checkNotNull(packetClass, "packetClass");

            BedrockPacketDefinition<? extends BedrockPacket> info = packets.remove(packetClass);
            return this;
        }

        public Builder protocolVersion(@Nonnegative int protocolVersion) {
            checkArgument(protocolVersion >= 0, "protocolVersion cannot be negative");
            this.protocolVersion = protocolVersion;
            return this;
        }

        public Builder raknetProtocolVersion(@Nonnegative int version) {
            checkArgument(version >= 0, "raknetProtocolVersion cannot be negative");
            this.raknetProtocolVersion = version;
            return this;
        }

        public Builder minecraftVersion(@Nonnull String minecraftVersion) {
            checkNotNull(minecraftVersion, "minecraftVersion");
            checkArgument(!minecraftVersion.isEmpty() && minecraftVersion.split("\\.").length > 2, "Invalid minecraftVersion");
            this.minecraftVersion = minecraftVersion;
            return this;
        }

        public Builder helper(@Nonnull Supplier<BedrockCodecHelper> helperFactory) {
            checkNotNull(helperFactory, "helperFactory");
            this.helperFactory = helperFactory;
            return this;
        }

        public BedrockCodec build() {
            checkArgument(protocolVersion >= 0, "No protocol version defined");
            checkNotNull(minecraftVersion, "No Minecraft version defined");
            checkNotNull(helperFactory, "helperFactory cannot be null");
            int largestId = -1;
            for (BedrockPacketDefinition<? extends BedrockPacket> info : packets.values()) {
                if (info.getId() > largestId) {
                    largestId = info.getId();
                }
            }
            checkArgument(largestId > -1, "Must have at least one packet registered");
            BedrockPacketDefinition<? extends BedrockPacket>[] packetsById = new BedrockPacketDefinition[largestId + 1];

            for (BedrockPacketDefinition<? extends BedrockPacket> info : packets.values()) {
                packetsById[info.getId()] = info;
            }
            return new BedrockCodec(protocolVersion, minecraftVersion, packetsById, packets, helperFactory, raknetProtocolVersion);
        }
    }
}
