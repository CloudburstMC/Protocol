package com.nukkitx.protocol.bedrock.session;

import com.nukkitx.network.raknet.session.RakNetSession;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.MinecraftSession;
import com.nukkitx.protocol.PlayerSession;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.compat.BedrockCompat;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.handler.TailHandler;
import com.nukkitx.protocol.bedrock.handler.WrapperTailHandler;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.session.data.AuthData;
import com.nukkitx.protocol.bedrock.session.data.ClientData;
import com.nukkitx.protocol.bedrock.wrapper.DefaultWrapperHandler;
import com.nukkitx.protocol.bedrock.wrapper.WrappedPacket;
import com.nukkitx.protocol.bedrock.wrapper.WrapperHandler;
import com.nukkitx.protocol.util.NativeCodeFactory;
import com.voxelwind.server.jni.hash.VoxelwindHash;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.jni.cipher.BungeeCipher;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class BedrockSession<PLAYER extends PlayerSession> implements MinecraftSession<PLAYER, RakNetSession> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockSession.class);
    private static final ThreadLocal<VoxelwindHash> hashLocal = ThreadLocal.withInitial(NativeCodeFactory.hash::newInstance);
    private static final InetSocketAddress LOOPBACK_BEDROCK = new InetSocketAddress(InetAddress.getLoopbackAddress(), 19132);

    @Getter(AccessLevel.NONE)
    private final Queue<BedrockPacket> currentlyQueued = new ConcurrentLinkedQueue<>();
    @Getter(AccessLevel.NONE)
    private final AtomicLong sentEncryptedPacketCount = new AtomicLong();
    private BedrockPacketCodec packetCodec = BedrockCompat.COMPAT_CODEC;
    private BedrockPacketHandler handler;
    private WrapperHandler wrapperHandler = DefaultWrapperHandler.DEFAULT;
    @Setter
    private TailHandler tailHandler = null;
    @Setter
    private WrapperTailHandler<PLAYER> wrapperTailHandler = null;
    private RakNetSession connection;
    private AuthData authData;
    private ClientData clientData;
    private BungeeCipher encryptionCipher = null;
    private BungeeCipher decryptionCipher = null;
    @Setter
    private PLAYER player;
    @Getter(AccessLevel.NONE)
    private SecretKey agreedKey;
    private int protocolVersion;
    @Setter
    private volatile boolean logging = true;

    public BedrockSession(RakNetSession connection) {
        this.connection = connection;
    }

    public BedrockSession(RakNetSession connection, BedrockPacketCodec codec) {
        this.connection = connection;
        this.packetCodec = codec;
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        Preconditions.checkNotNull(authData, "authData");
        this.authData = authData;
    }

    public void setHandler(@Nonnull BedrockPacketHandler handler) {
        checkForClosed();
        Preconditions.checkNotNull(handler, "handler");
        this.handler = handler;
    }

    public void setPacketCodec(BedrockPacketCodec packetCodec) {
        this.packetCodec = packetCodec;
    }

    public void setWrapperHandler(WrapperHandler wrapperHandler) {
        checkForClosed();
        Preconditions.checkNotNull(wrapperHandler, "wrapperCompressionHandler");
        this.wrapperHandler = wrapperHandler;
    }

    private void checkForClosed() {
        Preconditions.checkState(!connection.isClosed(), "Connection has been closed!");
    }

    public void sendPacket(BedrockPacket packet) {
        checkForClosed();
        Preconditions.checkNotNull(packet, "packet");
        if (log.isTraceEnabled() && logging) {
            String to = connection.getRemoteAddress().orElse(LOOPBACK_BEDROCK).toString();
            log.trace("Outbound {}: {}", to, packet);
        }

        // Verify that the packet ID exists.
        packetCodec.getId(packet);

        currentlyQueued.add(packet);
    }

    public void sendPacketImmediately(BedrockPacket packet) {
        checkForClosed();
        Preconditions.checkNotNull(packet, "packet");

        if (log.isTraceEnabled() && logging) {
            String to = connection.getRemoteAddress().orElse(LOOPBACK_BEDROCK).toString();
            log.trace("Outbound {}: {}", to, packet);
        }

        WrappedPacket<PLAYER> wrappedPacket = new WrappedPacket<>();
        wrappedPacket.getPackets().add(packet);
        if (packet.getClass().isAnnotationPresent(NoEncryption.class)) {
            wrappedPacket.setEncrypted(true);
        }
        sendWrapped(wrappedPacket);
    }

    public void sendWrapped(WrappedPacket<PLAYER> packet) {
        Preconditions.checkNotNull(packet, "packet");
        ByteBuf compressed;
        if (packet.getBatched() == null) {
            compressed = wrapperHandler.compressPackets(packetCodec, packet.getPackets());
        } else {
            compressed = packet.getBatched();
        }

        ByteBuf finalPayload = null;
        try {
            if (encryptionCipher == null || packet.isEncrypted()) {
                compressed.readerIndex(0);
                finalPayload = compressed;
            } else {
                finalPayload = PooledByteBufAllocator.DEFAULT.directBuffer();
                compressed.readerIndex(0);
                byte[] trailer = generateTrailer(compressed);
                compressed.writeBytes(trailer);

                compressed.readerIndex(0);
                encryptionCipher.cipher(compressed, finalPayload);
            }
        } catch (GeneralSecurityException e) {
            if (finalPayload != null) {
                finalPayload.release();
            }
            throw new RuntimeException("Unable to encrypt package", e);
        } finally {
            if (compressed != finalPayload) {
                compressed.release();
            }
        }
        packet.setPayload(finalPayload);

        connection.sendPacket(packet);
    }

    public void onTick() {
        if (connection.isClosed()) {
            return;
        }

        sendQueued();
    }

    private void sendQueued() {
        BedrockPacket packet;
        WrappedPacket<PLAYER> wrapper = new WrappedPacket<>();
        while ((packet = currentlyQueued.poll()) != null) {
            if (packet.getClass().isAnnotationPresent(NoEncryption.class)) {
                // We hit a wrappable packet. Send the current wrapper and then send the un-wrappable packet.
                if (!wrapper.getPackets().isEmpty()) {
                    sendWrapped(wrapper);
                    wrapper = new WrappedPacket<>();
                }

                sendPacketImmediately(packet);

                try {
                    // Delay things a tiny bit
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    log.error("Interrupted", e);
                }
            }

            wrapper.getPackets().add(packet);
        }

        if (!wrapper.getPackets().isEmpty()) {
            sendWrapped(wrapper);
        }
    }

    public void enableEncryption(@Nonnull SecretKey secretKey) {
        checkForClosed();
        Preconditions.checkNotNull(secretKey, "secretKey");
        Preconditions.checkArgument(secretKey.getAlgorithm().equals("AES"));
        Preconditions.checkState(encryptionCipher == null && decryptionCipher == null, "encryption has already been enabled");

        agreedKey = secretKey;
        byte[] iv = Arrays.copyOf(secretKey.getEncoded(), 16);
        try {
            encryptionCipher = NativeCodeFactory.cipher.newInstance();
            decryptionCipher = NativeCodeFactory.cipher.newInstance();

            encryptionCipher.init(true, secretKey, iv);
            decryptionCipher.init(false, secretKey, iv);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Unable to initialize ciphers", e);
        }

        connection.setUseOrdering(true);
    }

    private byte[] generateTrailer(ByteBuf buf) {
        VoxelwindHash hash = hashLocal.get();
        ByteBuf counterBuf = PooledByteBufAllocator.DEFAULT.directBuffer(8);
        ByteBuf keyBuf = PooledByteBufAllocator.DEFAULT.directBuffer(agreedKey.getEncoded().length);
        try {
            counterBuf.writeLongLE(sentEncryptedPacketCount.getAndIncrement());
            keyBuf.writeBytes(agreedKey.getEncoded());

            hash.update(counterBuf);
            hash.update(buf);
            hash.update(keyBuf);
            byte[] digested = hash.digest();
            return Arrays.copyOf(digested, 8);
        } finally {
            counterBuf.release();
            keyBuf.release();
        }
    }

    public boolean isEncrypted() {
        return encryptionCipher != null;
    }

    public void close() {
        // Free native resources if required
        if (encryptionCipher != null) {
            encryptionCipher.free();
        }
        if (decryptionCipher != null) {
            decryptionCipher.free();
        }

        // Destroy secret key
        if (agreedKey != null && !agreedKey.isDestroyed()) {
            try {
                agreedKey.destroy();
            } catch (DestroyFailedException e) {
                throw new RuntimeException(e);
            }
        }

        // Make sure the player is closed properly
        if (player != null) {
            player.close();
        }
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    public void setProtocolVersion(@Nonnegative int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public void disconnect() {
        disconnect(null, true);
    }

    @Override
    public void disconnect(@Nullable String reason) {
        disconnect(reason, false);
    }

    public void disconnect(@Nullable String reason, boolean hideReason) {
        checkForClosed();

        DisconnectPacket packet = new DisconnectPacket();
        if (reason == null || hideReason) {
            packet.setDisconnectScreenHidden(true);
            reason = "disconnect.disconnected";
        } else {
            packet.setKickMessage(reason);
        }
        sendPacketImmediately(packet);

        if (player != null) {
            player.onDisconnect(reason);
        }

        close();
    }

    @Override
    public void onTimeout() {
        player.onTimeout();

        close();
    }

    public void onWrappedPacket(WrappedPacket<PLAYER> packet) throws Exception {
        Preconditions.checkNotNull(packet, "packet");
        if (wrapperHandler == null) {
            return;
        }

        ByteBuf wrappedData = packet.getPayload();
        ByteBuf unwrappedData = null;
        try {
            if (isEncrypted()) {
                // Decryption
                unwrappedData = PooledByteBufAllocator.DEFAULT.directBuffer(wrappedData.readableBytes());
                decryptionCipher.cipher(wrappedData, unwrappedData);
                // TODO: Maybe verify the checksum?
                unwrappedData = unwrappedData.slice(0, unwrappedData.readableBytes() - 8);
            } else {
                // Encryption not enabled so it should be readable.
                unwrappedData = wrappedData;
            }

            packet.setBatched(unwrappedData);
            handleBatched(packet);
        } finally {
            wrappedData.release();
            if (unwrappedData != null && unwrappedData != wrappedData) {
                unwrappedData.release();
            }
        }
    }

    protected void handleBatched(WrappedPacket<PLAYER> wrappedPacket) {
        boolean handled = false;
        for (BedrockPacket packet : wrapperHandler.decompressPackets(packetCodec, wrappedPacket.getBatched())) {
            if (logging && log.isTraceEnabled()) {
                log.trace("Inbound {}: {}", getAddress(), packet);
            }
            if ((handler != null && packet.handle(handler)) || (tailHandler != null && tailHandler.handle(packet, handled))) {
                handled = true;
            }
        }
        if (wrapperTailHandler != null) {
            wrapperTailHandler.handle(wrappedPacket, handled);
        }
    }

    private String getAddress() {
        return getRemoteAddress().map(InetSocketAddress::toString).orElse("UNKNOWN");
    }

    @Override
    public boolean isClosed() {
        return connection.isClosed();
    }
}
