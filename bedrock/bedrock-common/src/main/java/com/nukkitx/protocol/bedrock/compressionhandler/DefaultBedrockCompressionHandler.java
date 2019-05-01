package com.nukkitx.protocol.bedrock.compressionhandler;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.util.Zlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;

public class DefaultBedrockCompressionHandler implements BedrockCompressionHandler {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(DefaultBedrockCompressionHandler.class);
    public static final DefaultBedrockCompressionHandler DEFAULT = new DefaultBedrockCompressionHandler();

    private final Zlib zlib;

    private DefaultBedrockCompressionHandler() {
        zlib = Zlib.DEFAULT;
    }

    public DefaultBedrockCompressionHandler(int compression) {
        zlib = new Zlib(compression);
    }

    @Override
    public ByteBuf compressPackets(BedrockPacketCodec packetCodec, Collection<BedrockPacket> packets) {
        ByteBuf source = PooledByteBufAllocator.DEFAULT.directBuffer();
        try {
            for (BedrockPacket packet : packets) {
                ByteBuf packetBuf = null;
                try {
                    packetBuf = packetCodec.tryEncode(packet);
                    VarInts.writeUnsignedInt(source, packetBuf.readableBytes());
                    source.writeBytes(packetBuf);
                } finally {
                    if (packetBuf != null) {
                        packetBuf.release();
                    }
                }
            }
            return zlib.deflate(source);
        } catch (DataFormatException e) {
            throw new RuntimeException("Unable to deflate buffer data", e);
        } finally {
            source.release();
        }
    }

    @Override
    public List<BedrockPacket> decompressPackets(BedrockPacketCodec packetCodec, ByteBuf compressed) {
        List<BedrockPacket> packets = new ArrayList<>();
        ByteBuf decompressed = null;
        try {
            decompressed = zlib.inflate(compressed);

            while (decompressed.isReadable()) {
                int length = VarInts.readUnsignedInt(decompressed);
                ByteBuf data = decompressed.readSlice(length);

                if (!data.isReadable()) {
                    throw new DataFormatException("Contained packet is empty.");
                }

                BedrockPacket pkg = packetCodec.tryDecode(data);
                packets.add(pkg);
            }
        } catch (DataFormatException e) {
            throw new RuntimeException("Unable to inflate buffer data", e);
        } finally {
            if (decompressed != null) {
                decompressed.release();
            }
        }
        return packets;
    }
}
