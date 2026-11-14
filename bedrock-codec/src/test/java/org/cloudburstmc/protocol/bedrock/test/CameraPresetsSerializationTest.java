package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistPreset;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.bedrock.packet.CameraPresetsPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CameraPresetsSerializationTest {
    private static final int PACKET_ID = 198;

    // Sent by BDS 1.26.51 with a behavior pack adding two presets with aim assist, target mode "angle" and "distance"
    private static final String BDS_PACKET =
            "08166d696e6563726166743a66697273745f706572736f6e000000000000000000000000000000000000000000010014" +
            "6d696e6563726166743a66697865645f626f6f6d00000000000000000000000001000000000000000001000000000000" +
            "000000000000000000000000000100166d696e6563726166743a666f6c6c6f775f6f7262697400000000000000000000" +
            "000001000000000000000001000000000000000000000000010000204100000000000001000e6d696e6563726166743a" +
            "667265650001000000000100000000010000000001000000000100000000000000000000000000000000000000010016" +
            "6d696e6563726166743a74686972645f706572736f6e00000000000000000000000000000000000000000001001c6d69" +
            "6e6563726166743a74686972645f706572736f6e5f66726f6e7400000000000000000000000000000000000000000001" +
            "0012646f63636865636b3a61696d5f616e676c650e6d696e6563726166743a6672656500000000000000000000000000" +
            "000000000001011c6d696e6563726166743a61696d5f6173736973745f64656661756c74010001000048420000704201" +
            "0000184100010015646f63636865636b3a61696d5f64697374616e63650e6d696e6563726166743a6672656500000000" +
            "000000000000000000000000000001011c6d696e6563726166743a61696d5f6173736973745f64656661756c74010101" +
            "0000f04100002042010000f040000100";

    // The same presets from BDS 1.21.50, the first version with aim assist presets
    private static final String BDS_PACKET_V766 =
            "080e6d696e6563726166743a667265650001000000000100000000010000000001000000000100000000000000000000" +
            "00000000000000166d696e6563726166743a666f6c6c6f775f6f72626974000000000000000000000000010000000000" +
            "00000001000000800000000000000000010000204100000000166d696e6563726166743a66697273745f706572736f6e" +
            "000000000000000000000000000000000000001c6d696e6563726166743a74686972645f706572736f6e5f66726f6e74" +
            "00000000000000000000000000000000000000166d696e6563726166743a74686972645f706572736f6e000000000000" +
            "00000000000000000000000001011c6d696e6563726166743a61696d5f6173736973745f64656661756c74000000146d" +
            "696e6563726166743a66697865645f626f6f6d0000000000000000000000000100000000000000000100000080000000" +
            "000000000000000001000012646f63636865636b3a61696d5f616e676c650e6d696e6563726166743a66726565000000" +
            "000000000000000000000000000001011c6d696e6563726166743a61696d5f6173736973745f64656661756c74010001" +
            "0000484200007042010000184115646f63636865636b3a61696d5f64697374616e63650e6d696e6563726166743a6672" +
            "6565000000000000000000000000000000000001011c6d696e6563726166743a61696d5f6173736973745f6465666175" +
            "6c740101010000f04100002042010000f040";

    @Test
    void aimAssistTargetModeIsOneByte() throws Exception {
        assertBdsPacket(Bedrock_v2193.CODEC, BDS_PACKET);
        assertBdsPacket(Bedrock_v766.CODEC, BDS_PACKET_V766);
    }

    private static void assertBdsPacket(BedrockCodec codec, String hex) throws Exception {
        String name = "v" + codec.getProtocolVersion();
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        CameraPresetsPacket packet = (CameraPresetsPacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), name + " left bytes unread");

        assertAimAssist(packet, "doccheck:aim_angle", 0, Vector2f.from(50, 60), 9.5f);
        assertAimAssist(packet, "doccheck:aim_distance", 1, Vector2f.from(30, 40), 7.5f);

        ByteBuf encoded = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), encoded, packet);
            assertEquals(hex, ByteBufUtil.hexDump(encoded), name);
        } finally {
            encoded.release();
        }
    }

    private static void assertAimAssist(CameraPresetsPacket packet, String identifier, int targetMode, Vector2f angle, float distance) {
        CameraPreset preset = packet.getPresets().stream()
                .filter(candidate -> identifier.equals(candidate.getIdentifier()))
                .findFirst().orElseThrow(AssertionError::new);
        CameraAimAssistPreset aimAssist = preset.getAimAssistPreset();
        assertEquals("minecraft:aim_assist_default", aimAssist.getIdentifier(), identifier);
        assertEquals(targetMode, aimAssist.getTargetMode(), identifier);
        assertEquals(angle, aimAssist.getAngle(), identifier);
        assertEquals(distance, aimAssist.getDistance(), identifier);
    }
}
