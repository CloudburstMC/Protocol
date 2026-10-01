package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.codec.v786.Bedrock_v786;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.RiderJumpPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiderJumpSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v291.CODEC, Bedrock_v786.CODEC};
    private static final int PACKET_ID = 20;

    // The jump strength is a signed varint, and was already read as one
    @Test
    void jumpStrengthIsSignedVarInt() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            RiderJumpPacket packet = new RiderJumpPacket();
            packet.setJumpStrength(50);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                assertEquals("64", ByteBufUtil.hexDump(buffer), name);
                RiderJumpPacket decoded = (RiderJumpPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.SERVER);
                assertEquals(50, decoded.getJumpStrength(), name);
            } finally {
                buffer.release();
            }
        }
    }
}
