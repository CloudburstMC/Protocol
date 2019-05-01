package com.nukkitx.protocol.bedrock.compressionhandler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

public interface BedrockCompressionHandler {
    ByteBuf compressPackets(BedrockPacketCodec packetCodec, Collection<BedrockPacket> packets);

    Collection<BedrockPacket> decompressPackets(BedrockPacketCodec packetCodec, ByteBuf compressed);
}