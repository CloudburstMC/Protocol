package org.cloudburstmc.protocol.bedrock.wrapper;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.cloudburstmc.protocol.bedrock.BedrockSession;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

import java.util.Collection;

public abstract class BedrockWrapperSerializer {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockWrapperSerializerV9_10.class);

    /**
     * Compress packets to a buffer for sending
     *
     * @param buffer buffer to write batched packets to
     * @param codec  packet codec
     * @param level  compression level
     */
    public abstract void serialize(ByteBuf buffer, BedrockCodec codec, Collection<BedrockPacket> packets, int level, BedrockSession session);

    /**
     * Decompress packets to handle
     *
     * @param buffer  buffer to write batched packets to
     * @param codec   packet codec
     * @param packets received packets
     */
    public abstract void deserialize(ByteBuf buffer, BedrockCodec codec, Collection<BedrockPacket> packets, BedrockSession session);
}
