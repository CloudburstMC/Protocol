package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.v944.serializer.CameraInstructionSerializer_v944;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
