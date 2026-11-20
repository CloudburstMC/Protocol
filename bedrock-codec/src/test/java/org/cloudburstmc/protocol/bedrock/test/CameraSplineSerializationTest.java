package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineDefinition;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineType;
import org.cloudburstmc.protocol.bedrock.packet.CameraSplinePacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CameraSplineSerializationTest {
    private static final int PACKET_ID = 338;

    // Sent by BDS 1.26.51 for a behavior pack with two splines in cameras/splines
    private static final String BDS_PACKET =
            "020e646f63636865636b3a6669727374000080400a6361746d756c6c726f6d04000000000000a0420000000000002041" +
            "0000a442000000000000a0410000a842000020410000f0410000a0420000000002000000000000000001066c696e6561" +
            "720000803f00008040010b696e5f6f75745f73696e6501000000000000b442000000000000000001066c696e6561720f" +
            "646f63636865636b3a7365636f6e6400000040066c696e656172020000a04000008c420000a0400000c04000008e4200" +
            "00c04001000000000000000001066c696e65617201000020410000a0410000f0410000803f01066c696e656172";

    @Test
    void decodesSplinesFromBds() throws Exception {
        BedrockCodec codec = Bedrock_v2193.CODEC;
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(BDS_PACKET));
        CameraSplinePacket packet = (CameraSplinePacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), "left bytes unread");

        assertEquals(2, packet.getSplines().size());
        CameraSplineDefinition first = packet.getSplines().get(0);
        assertEquals("doccheck:first", first.getName());
        CameraSplineInstruction instruction = first.getInstruction();
        assertEquals(4f, instruction.getTotalTime());
        assertEquals(CameraSplineType.CATMULL_ROM, instruction.getType());
        assertEquals(4, instruction.getCurve().size());
        assertEquals(CameraEase.EASE_IN_OUT_SINE, instruction.getProgressKeyFrames().get(1).getEase());
        assertEquals(Vector3f.from(0, 90, 0), instruction.getRotationOption().get(0).getKeyFrameValues());
        assertEquals("doccheck:second", packet.getSplines().get(1).getName());

        ByteBuf encoded = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), encoded, packet);
            assertEquals(BDS_PACKET, ByteBufUtil.hexDump(encoded));
        } finally {
            encoded.release();
        }
    }
}
