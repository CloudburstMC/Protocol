package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v1001.Bedrock_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v748.Bedrock_v748;
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766;
import org.cloudburstmc.protocol.bedrock.data.MovementEffectType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.MovementEffectPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MovementEffectSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v748.CODEC, Bedrock_v2193.CODEC};
    private static final int PACKET_ID = 318;

    // Sent by BDS 1.26.51 to a real client: a firework boost while gliding, then swimming beside a dolphin
    @Test
    void decodesEffectsFromBds() throws Exception {
        assertDecodes(Bedrock_v2193.CODEC, "1b0038f701", 27, MovementEffectType.GLIDE_BOOST, 28, 247);
        assertDecodes(Bedrock_v2193.CODEC, "1b0278f20a", 27, MovementEffectType.DOLPHIN_BOOST, 60, 1394);
        // BDS 1.21.50 and 1.26.30
        assertDecodes(Bedrock_v766.CODEC, "0d002e00", 13, MovementEffectType.GLIDE_BOOST, 23, 0);
        assertDecodes(Bedrock_v1001.CODEC, "0b0278ce0c", 11, MovementEffectType.DOLPHIN_BOOST, 60, 1614);
    }

    private static void assertDecodes(BedrockCodec codec, String hex, long runtimeId, MovementEffectType type, int duration, long tick) throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        MovementEffectPacket packet = (MovementEffectPacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), hex + " left bytes unread");
        assertEquals(runtimeId, packet.getEntityRuntimeId(), hex);
        assertEquals(type, packet.getEffectType(), hex);
        assertEquals(duration, packet.getDuration(), hex);
        assertEquals(tick, packet.getTick(), hex);
    }

    // Effect ID and Effect Duration are signed varints in the protocol docs for 748 through 2193
    @Test
    void effectAndDurationAreSignedVarInts() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            MovementEffectPacket packet = new MovementEffectPacket();
            packet.setEntityRuntimeId(1);
            packet.setEffectType(MovementEffectType.DOLPHIN_BOOST);
            packet.setDuration(20);
            packet.setTick(3);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                assertEquals("01022803", ByteBufUtil.hexDump(buffer), name);

                MovementEffectPacket decoded = (MovementEffectPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
                assertEquals(MovementEffectType.DOLPHIN_BOOST, decoded.getEffectType(), name);
                assertEquals(20, decoded.getDuration(), name);
                assertFalse(buffer.isReadable(), name + " left bytes unread");
            } finally {
                buffer.release();
            }
        }
    }
}
