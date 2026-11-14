package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.codec.v662.Bedrock_v662;
import org.cloudburstmc.protocol.bedrock.codec.v860.Bedrock_v860;
import org.cloudburstmc.protocol.bedrock.codec.v898.Bedrock_v898;
import org.cloudburstmc.protocol.bedrock.codec.v924.Bedrock_v924;
import org.cloudburstmc.protocol.bedrock.data.ClientboundDebugRendererType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDebugRendererPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientboundDebugRendererSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v428.CODEC, Bedrock_v860.CODEC};
    private static final int PACKET_ID = 164;
    private static final String TEXT = "Expected Block of type 'minecraft:diamond_block'";

    // Sent by BDS for /gametest clearall and a GameTest whose assertion failed at a block (the red marker cube)
    @Test
    void capturedPacketsDecode() throws Exception {
        assertClear(Bedrock_v662.CODEC, "01000000");
        assertClear(Bedrock_v860.CODEC, "01000000");
        assertCube(Bedrock_v860.CODEC, "0200000030457870656374656420426c6f636b206f66207479706520276d696e6563726166743a6469616d6f6e645f626c6f636b27"
                + "0000000000006cc2000040400000803f00000000000000000000803f005c260500000000", Vector3f.from(0, -59, 3));
        // From v898 the type is a name and the marker data optional, with an ARGB color
        assertClear(Bedrock_v898.CODEC, "11636c65617264656275676d61726b65727300");
        assertCube(Bedrock_v898.CODEC, "1261646464656275676d61726b657263756265013045787065637465642042"
                + "6c6f636b206f66207479706520276d696e6563726166743a6469616d6f6e645f626c6f636b270000000000006cc2000040400000ffff005c260500000000",
                Vector3f.from(0, -59, 3));
        assertClear(Bedrock_v924.CODEC, "11636c65617264656275676d61726b65727300");
        assertCube(Bedrock_v924.CODEC, "1261646464656275676d61726b657263756265013045787065637465642042"
                + "6c6f636b206f66207479706520276d696e6563726166743a6469616d6f6e645f626c6f636b270000000000006cc2000040400000ffff005c260500000000",
                Vector3f.from(0, -59, 3));
        assertClear(Bedrock_v2169.CODEC, "11636c65617264656275676d61726b65727300");
        assertCube(Bedrock_v2169.CODEC, "1261646464656275676d61726b657263756265013045787065637465642042"
                + "6c6f636b206f66207479706520276d696e6563726166743a6469616d6f6e645f626c6f636b270000000000008e42000040400000ffff005c260500000000",
                Vector3f.from(0, 71, 3));
    }

    private static void assertClear(BedrockCodec codec, String hex) throws Exception {
        ClientboundDebugRendererPacket packet = decodeAndReencode(codec, hex);
        assertEquals(ClientboundDebugRendererType.CLEAR_DEBUG_MARKERS, packet.getDebugMarkerType(), "v" + codec.getProtocolVersion());
    }

    private static void assertCube(BedrockCodec codec, String hex, Vector3f position) throws Exception {
        String name = "v" + codec.getProtocolVersion();
        ClientboundDebugRendererPacket packet = decodeAndReencode(codec, hex);
        assertEquals(ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE, packet.getDebugMarkerType(), name);
        assertEquals(TEXT, packet.getMarkerText(), name);
        assertEquals(position, packet.getMarkerPosition(), name);
        assertEquals(1f, packet.getMarkerColorRed(), name);
        assertEquals(0f, packet.getMarkerColorGreen(), name);
        assertEquals(0f, packet.getMarkerColorBlue(), name);
        assertEquals(1f, packet.getMarkerColorAlpha(), name);
        assertEquals(86_400_000L, packet.getMarkerDuration(), name);
    }

    private static ClientboundDebugRendererPacket decodeAndReencode(BedrockCodec codec, String hex) throws Exception {
        String name = "v" + codec.getProtocolVersion();
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        ClientboundDebugRendererPacket packet = (ClientboundDebugRendererPacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), name + " left bytes unread");

        ByteBuf encoded = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), encoded, packet);
            assertEquals(hex, ByteBufUtil.hexDump(encoded), name);
        } finally {
            encoded.release();
        }
        return packet;
    }

    // The marker color is four little endian floats, like every other float on the wire
    @Test
    void markerColorIsLittleEndian() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            ClientboundDebugRendererPacket packet = new ClientboundDebugRendererPacket();
            packet.setDebugMarkerType(ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE);
            packet.setMarkerText("");
            packet.setMarkerPosition(Vector3f.ZERO);
            packet.setMarkerColorRed(0.5f);
            packet.setMarkerColorGreen(0.25f);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                assertTrue(ByteBufUtil.hexDump(buffer).contains("0000003f0000803e"), name);
            } finally {
                buffer.release();
            }
        }
    }
}
