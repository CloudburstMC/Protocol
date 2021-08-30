package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.bedrock.exception.PacketSerializeException;
import com.nukkitx.protocol.bedrock.packet.UnknownPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

import static com.nukkitx.network.util.Preconditions.*;

@Immutable
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BedrockPacketCodec {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPacketCodec.class);

    @Getter
    private final int protocolVersion;
    @Getter
    private final String minecraftVersion;
    private final BedrockPacketDefinition<? extends BedrockPacket>[] packetsById;
    private final Map<Class<? extends BedrockPacket>, BedrockPacketDefinition<? extends BedrockPacket>> packetsByClass;
    @Getter
    private final BedrockPacketHelper helper;
    @Getter
    private final int raknetProtocolVersion;

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public BedrockPacket tryDecode(ByteBuf buf, int id, BedrockSession session) throws PacketSerializeException {
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
            serializer.deserialize(buf, this.helper, packet, session);
        } catch (Exception e) {
            throw new PacketSerializeException("Error whilst deserializing " + packet, e);
        }

        if (log.isDebugEnabled() && buf.isReadable()) {
            log.debug(packet.getClass().getSimpleName() + " still has " + buf.readableBytes() + " bytes to read!");
        }
        return packet;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void tryEncode(ByteBuf buf, BedrockPacket packet, BedrockSession session) throws PacketSerializeException {
        try {
            BedrockPacketSerializer<BedrockPacket> serializer;
            if (packet instanceof UnknownPacket) {
                serializer = (BedrockPacketSerializer<BedrockPacket>) packet;
            } else {
                BedrockPacketDefinition definition = getPacketDefinition(packet.getClass());
                serializer = definition.getSerializer();
            }
            serializer.serialize(buf, this.helper, packet, session);
        } catch (Exception e) {
            throw new PacketSerializeException("Error whilst serializing " + packet, e);
        } finally {
            ReferenceCountUtil.release(packet);
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

    public int getId(BedrockPacket packet) {
        if (packet instanceof UnknownPacket) {
            return packet.getPacketId();
        }
        return getId(packet.getClass());
    }

    public int getId(Class<? extends BedrockPacket> clazz) {
        BedrockPacketDefinition<?> definition = getPacketDefinition(clazz);
        if (definition == null) {
            throw new IllegalArgumentException("Packet ID for " + clazz.getName() + " does not exist.");
        }
        return definition.getId();
    }

    public Builder toBuilder() {
        Builder builder = new Builder();

        builder.packets.putAll(this.packetsByClass);
        builder.protocolVersion = this.protocolVersion;
        builder.raknetProtocolVersion = this.raknetProtocolVersion;
        builder.minecraftVersion = this.minecraftVersion;
        builder.helper = this.helper;

        return builder;
    }

    @SuppressWarnings("unchecked")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final Map<Class<? extends BedrockPacket>, BedrockPacketDefinition<? extends BedrockPacket>> packets = new IdentityHashMap<>();
        private int protocolVersion = -1;
        private int raknetProtocolVersion = 10;
        private String minecraftVersion = null;
        private BedrockPacketHelper helper = null;

        @SuppressWarnings({"ConstantConditions", "rawtypes"})
        public <T extends BedrockPacket> Builder registerPacket(Class<T> packetClass, BedrockPacketSerializer<T> serializer, @Nonnegative int id) {
            checkArgument(id >= 0, "id cannot be negative");
            checkArgument(!this.packets.containsKey(packetClass), "Packet class already registered");

            Supplier<Object> factory;
            try {
                MethodHandles.Lookup lookup = MethodHandlesExtensions.privateLookupIn(packetClass, MethodHandles.lookup());
                MethodHandle handle = lookup.findConstructor(packetClass, MethodType.methodType(void.class));
                factory = LambdaFactory.createSupplier(handle);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalArgumentException("Unable to find suitable constructor for packet factory", e);
            }

            BedrockPacketDefinition<T> info = new BedrockPacketDefinition<>(id, (Supplier) factory, serializer);

            this.packets.put(packetClass, info);

            return this;
        }

        public void deregisterPacket(Class<? extends BedrockPacket> packetClass) {
            checkNotNull(packetClass, "packetClass");

            BedrockPacketDefinition<? extends BedrockPacket> info = packets.remove(packetClass);
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

        public Builder helper(@Nonnull BedrockPacketHelper helper) {
            checkNotNull(helper, "helper");
            this.helper = helper;
            return this;
        }

        public BedrockPacketCodec build() {
            checkArgument(protocolVersion >= 0, "No protocol version defined");
            checkNotNull(minecraftVersion, "No Minecraft version defined");
            checkNotNull(helper, "helper cannot be null");
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
            return new BedrockPacketCodec(protocolVersion, minecraftVersion, packetsById, packets, helper, raknetProtocolVersion);
        }
    }
}
