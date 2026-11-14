package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v1001.Bedrock_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v975.Bedrock_v975;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.debugshape.DebugBox;
import org.cloudburstmc.protocol.bedrock.packet.DebugDrawerPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DebugDrawerSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v975.CODEC, Bedrock_v1001.CODEC, Bedrock_v2193.CODEC};
    private static final int PACKET_ID = 328;

    // StartGame unique id of the player in BDS 1.26.x captures
    private static final long PLAYER_UNIQUE_ID = -4294967295L;

    // Sent by BDS 1.26.30 for a debug-utilities DebugBox attached to the player, whose StartGame unique id was -8589934586
    @Test
    void capturedAttachedIdDecodes() throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump("01898180809081808001010101b4c0e63f000070c2969f00c1010000803f"
                + "01000000000000000000000000010000204101000080bf01ffffffff010601f3ffffff3f030000803f0000803f0000803f"));
        DebugDrawerPacket packet = (DebugDrawerPacket) Bedrock_v1001.CODEC.tryDecode(
                Bedrock_v1001.CODEC.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), "left bytes unread");
        assertEquals(-8589934586L, packet.getShapes().get(0).getAttachedToEntityId());
    }

    @Test
    void attachedEntityIdRoundTrips() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            DebugBox box = new DebugBox();
            box.setId(1);
            box.setBoxBounds(Vector3f.ONE);
            box.setAttachedToEntityId(PLAYER_UNIQUE_ID);
            DebugDrawerPacket packet = new DebugDrawerPacket();
            packet.getShapes().add(box);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                DebugDrawerPacket decoded = (DebugDrawerPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
                assertEquals(PLAYER_UNIQUE_ID, decoded.getShapes().get(0).getAttachedToEntityId(), name);
                assertFalse(buffer.isReadable(), name + " left bytes unread");
            } finally {
                buffer.release();
            }
        }
    }
}
