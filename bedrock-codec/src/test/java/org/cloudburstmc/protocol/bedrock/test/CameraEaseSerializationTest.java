package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v662.Bedrock_v662;
import org.cloudburstmc.protocol.bedrock.codec.v860.Bedrock_v860;
import org.cloudburstmc.protocol.bedrock.codec.v944.serializer.CameraInstructionSerializer_v944;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleNamedDefinition;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.SimpleDefinitionRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CameraEaseSerializationTest {

    // What BDS 1.26.51 sent for these eases in a capture of /camera ... set ... ease
    @Test
    void easeIdsFollowTheClient() {
        Eases eases = new Eases();
        assertEquals(0, eases.write(CameraEase.LINEAR));
        assertEquals(1, eases.write(CameraEase.SPRING));
        assertEquals(2, eases.write(CameraEase.EASE_IN_QUAD));
        assertEquals(16, eases.write(CameraEase.EASE_IN_OUT_SINE));
        assertEquals(23, eases.write(CameraEase.EASE_IN_BOUNCE));
        assertEquals(31, eases.write(CameraEase.EASE_IN_OUT_ELASTIC));

        for (CameraEase ease : CameraEase.values()) {
            assertEquals(ease, eases.read(eases.write(ease)), ease.name());
        }
    }

    // BDS 1.20.70 for /camera @s set minecraft:free ease 2 in_out_sine, BDS 1.21.124 for /camera @s fov_set 30 2 in_out_sine
    @Test
    void capturedEasesDecode() throws Exception {
        CameraInstructionPacket set = decode(Bedrock_v662.CODEC, "010100000001100000004001352dd7c1000058c2579abf410000000000");
        assertEquals(CameraEase.EASE_IN_OUT_SINE, set.getSetInstruction().getEase().getEaseType());
        CameraInstructionPacket fov = decode(Bedrock_v860.CODEC, "0000000000010000f041000000401000000000");
        assertEquals(CameraEase.EASE_IN_OUT_SINE, fov.getFovInstruction().getEaseType());
    }

    private static CameraInstructionPacket decode(BedrockCodec codec, String hex) throws Exception {
        BedrockCodecHelper helper = codec.createHelper();
        helper.setCameraPresetDefinitions(SimpleDefinitionRegistry.<NamedDefinition>builder()
                .add(new SimpleNamedDefinition("minecraft:free", 1))
                .build());
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        CameraInstructionPacket packet = (CameraInstructionPacket) codec.tryDecode(helper, buffer, 300, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), hex + " left bytes unread");
        ByteBuf encoded = Unpooled.buffer();
        try {
            codec.tryEncode(helper, encoded, packet);
            assertEquals(hex, ByteBufUtil.hexDump(encoded));
        } finally {
            encoded.release();
        }
        return packet;
    }

    private static final class Eases extends CameraInstructionSerializer_v944 {

        int write(CameraEase ease) {
            ByteBuf buffer = Unpooled.buffer();
            try {
                writeEase(buffer, new CameraSetInstruction.EaseData(ease, 1f));
                return buffer.getUnsignedByte(0);
            } finally {
                buffer.release();
            }
        }

        CameraEase read(int id) {
            ByteBuf buffer = Unpooled.buffer();
            try {
                buffer.writeByte(id);
                buffer.writeFloatLE(1f);
                return readEase(buffer).getEaseType();
            } finally {
                buffer.release();
            }
        }
    }
}
