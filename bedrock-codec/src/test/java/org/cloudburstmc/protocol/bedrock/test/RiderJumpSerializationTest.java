package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.codec.v503.Bedrock_v503;
import org.cloudburstmc.protocol.bedrock.codec.v786.Bedrock_v786;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.RiderJumpPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiderJumpSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v291.CODEC, Bedrock_v786.CODEC};
    private static final int PACKET_ID = 20;

    // Sent by a 1.18.33 client (client authoritative movement): a quick tap, a full charge and one between
    @Test
    void capturedJumpsDecode() throws Exception {
        String[] captured = {"14", "ac01", "c801"};
        int[] strengths = {10, 86, 100};
        for (int i = 0; i < captured.length; i++) {
            ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(captured[i]));
            RiderJumpPacket packet = (RiderJumpPacket) Bedrock_v503.CODEC.tryDecode(
                    Bedrock_v503.CODEC.createHelper(), buffer, PACKET_ID, PacketRecipient.SERVER);
            assertEquals(strengths[i], packet.getJumpStrength(), captured[i]);
            ByteBuf encoded = Unpooled.buffer();
            Bedrock_v503.CODEC.tryEncode(Bedrock_v503.CODEC.createHelper(), encoded, packet);
            assertEquals(captured[i], ByteBufUtil.hexDump(encoded));
            encoded.release();
        }
    }

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
