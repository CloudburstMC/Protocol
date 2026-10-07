package org.cloudburstmc.protocol.bedrock.netty.initializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CorruptedFrameException;
import org.cloudburstmc.protocol.bedrock.PacketDirection;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.batch.BedrockBatchDecoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BedrockChannelInitializerTest {

    @Test
    public void clientChannelsAcceptServerSizedBatches() {
        assertAccepted(PacketDirection.SERVER_BOUND, BedrockBatchDecoder.DEFAULT_MAX_SERVER_PACKETS);
        assertRefused(PacketDirection.SERVER_BOUND, BedrockBatchDecoder.DEFAULT_MAX_SERVER_PACKETS + 1);
    }

    @Test
    public void serverChannelsKeepTheClientLimit() {
        assertAccepted(PacketDirection.CLIENT_BOUND, BedrockBatchDecoder.DEFAULT_MAX_PACKETS);
        assertRefused(PacketDirection.CLIENT_BOUND, BedrockBatchDecoder.DEFAULT_MAX_PACKETS + 1);
    }

    @Test
    public void channelsWithoutDirectionKeepTheClientLimit() {
        assertRefused(null, BedrockBatchDecoder.DEFAULT_MAX_PACKETS + 1);
    }

    private static void assertAccepted(PacketDirection direction, int packets) {
        EmbeddedChannel channel = new EmbeddedChannel(BedrockChannelInitializer.batchDecoder(direction));
        try {
            channel.writeInbound(batch(packets));
            for (int i = 0; i < packets; i++) {
                ByteBuf packet = channel.readInbound();
                packet.release();
            }
            assertNull(channel.readInbound());
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    private static void assertRefused(PacketDirection direction, int packets) {
        EmbeddedChannel channel = new EmbeddedChannel(BedrockChannelInitializer.batchDecoder(direction));
        try {
            assertThrows(CorruptedFrameException.class, () -> channel.writeInbound(batch(packets)));
            assertNull(channel.readInbound());
        } finally {
            channel.finishAndReleaseAll();
        }
    }

    private static BedrockBatchWrapper batch(int packets) {
        ByteBuf uncompressed = Unpooled.buffer(packets * 2);
        for (int i = 0; i < packets; i++) {
            uncompressed.writeByte(1);
            uncompressed.writeByte(0);
        }
        return BedrockBatchWrapper.newInstance(null, uncompressed);
    }
}
