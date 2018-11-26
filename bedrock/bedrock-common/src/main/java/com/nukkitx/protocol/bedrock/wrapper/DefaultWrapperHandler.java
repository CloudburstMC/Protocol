package com.nukkitx.protocol.bedrock.wrapper;

import com.nukkitx.network.PacketCodec;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.util.Zlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.Cleanup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;

public class DefaultWrapperHandler implements WrapperHandler {
    public static final DefaultWrapperHandler DEFAULT = new DefaultWrapperHandler();

    private final Zlib zlib;

    private DefaultWrapperHandler() {
        zlib = Zlib.DEFAULT;
    }

    public DefaultWrapperHandler(int compression) {
        zlib = new Zlib(compression);
    }

    @Override
    public ByteBuf compressPackets(PacketCodec<BedrockPacket> packetCodec, Collection<BedrockPacket> packets) {
        ByteBuf source = PooledByteBufAllocator.DEFAULT.directBuffer();
        try {
            for (BedrockPacket packet : packets) {
                @Cleanup("release") ByteBuf packetBuf = packetCodec.tryEncode(packet);
                VarInts.writeUnsignedInt(source, packetBuf.readableBytes());
                source.writeBytes(packetBuf);
            }
            return zlib.deflate(source);
        } catch (DataFormatException e) {
            throw new RuntimeException("Unable to deflate buffer data", e);
        } finally {
            source.release();
        }
    }

    @Override
    public List<BedrockPacket> decompressPackets(PacketCodec<BedrockPacket> packetCodec, ByteBuf compressed) {
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
