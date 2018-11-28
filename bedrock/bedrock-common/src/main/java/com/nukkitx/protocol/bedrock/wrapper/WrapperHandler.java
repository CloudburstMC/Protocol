package com.nukkitx.protocol.bedrock.wrapper;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

public interface WrapperHandler {
    ByteBuf compressPackets(BedrockPacketCodec packetCodec, Collection<BedrockPacket> packets);

    Collection<BedrockPacket> decompressPackets(BedrockPacketCodec packetCodec, ByteBuf compressed);
}