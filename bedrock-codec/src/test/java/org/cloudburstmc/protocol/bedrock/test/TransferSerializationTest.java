package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.TransferPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v291.CODEC, Bedrock_v2193.CODEC};
    private static final int PACKET_ID = 85;

    // The port is an unsigned 16 bit integer
    @Test
    void portAbove32767RoundTrips() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            TransferPacket packet = new TransferPacket();
            packet.setAddress("example.com");
            packet.setPort(40000);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                TransferPacket decoded = (TransferPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
                assertEquals(40000, decoded.getPort(), name);
            } finally {
                buffer.release();
            }
        }
    }
}
