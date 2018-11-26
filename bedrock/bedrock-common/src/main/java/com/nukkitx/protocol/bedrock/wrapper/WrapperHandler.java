package com.nukkitx.protocol.bedrock.wrapper;

import com.nukkitx.network.PacketCodec;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

public interface WrapperHandler {
    ByteBuf compressPackets(PacketCodec<BedrockPacket> packetCodec, Collection<BedrockPacket> packets);

    Collection<BedrockPacket> decompressPackets(PacketCodec<BedrockPacket> packetCodec, ByteBuf compressed);
}