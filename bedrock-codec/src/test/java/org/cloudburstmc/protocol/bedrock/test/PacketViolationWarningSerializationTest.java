package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.PacketViolationWarningPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PacketViolationWarningSerializationTest {
    private static final int PACKET_ID = 156;

    // Sent by BDS 1.26.51 to a client whose login it refused
    private static final String BDS_PACKET = "0004023c436f6e6e656374696f6e205265717565737420696e76616c69642e0a" +
            "726561644e6f486561646572206661696c656421207061636b657449643a2031";

    @Test
    void decodesWhenSentToTheClient() throws Exception {
        BedrockCodec codec = Bedrock_v2193.CODEC;
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(BDS_PACKET));
        PacketViolationWarningPacket packet = (PacketViolationWarningPacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), "left bytes unread");
        assertEquals(1, packet.getPacketCauseId());
        assertEquals("Connection Request invalid.\nreadNoHeader failed! packetId: 1", packet.getContext());
    }
}
