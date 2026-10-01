package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v748.Bedrock_v748;
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
        assertDecodes("1b0038f701", MovementEffectType.GLIDE_BOOST, 28, 247);
        assertDecodes("1b0278f20a", MovementEffectType.DOLPHIN_BOOST, 60, 1394);
    }

    private static void assertDecodes(String hex, MovementEffectType type, int duration, long tick) throws Exception {
        BedrockCodec codec = Bedrock_v2193.CODEC;
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        MovementEffectPacket packet = (MovementEffectPacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), hex + " left bytes unread");
        assertEquals(27, packet.getEntityRuntimeId(), hex);
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
