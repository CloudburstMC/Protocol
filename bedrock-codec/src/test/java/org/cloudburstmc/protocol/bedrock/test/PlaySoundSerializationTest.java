package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaySoundSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v2168.CODEC, Bedrock_v2169.CODEC};

    // Sent by BDS 1.26.45 for player.playSound("random.orb", { loopCount }) with -1, 0 and 3
    @Test
    void capturedLoopCountsDecode() throws Exception {
        String prefix = "0a72616e646f6d2e6f726208a507080000803f0000803f";
        String[] captured = {prefix + "01010100000000000000", prefix + "00010200000000000000", prefix + "06010300000000000000"};
        int[] loops = {-1, 0, 3};
        for (int i = 0; i < captured.length; i++) {
            ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(captured[i]));
            PlaySoundPacket packet = (PlaySoundPacket) Bedrock_v2169.CODEC.tryDecode(
                    Bedrock_v2169.CODEC.createHelper(), buffer, 86, PacketRecipient.CLIENT);
            assertEquals(loops[i], packet.getLoopCount(), captured[i]);
            ByteBuf encoded = Unpooled.buffer();
            Bedrock_v2169.CODEC.tryEncode(Bedrock_v2169.CODEC.createHelper(), encoded, packet);
            assertEquals(captured[i], ByteBufUtil.hexDump(encoded));
            encoded.release();
        }
    }

    // The loop count is a signed varint in the 2169 docs, as b884d834 made it for v2193
    @Test
    void loopCountIsSignedVarInt() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            PlaySoundPacket packet = new PlaySoundPacket();
            packet.setSound("random.click");
            packet.setPosition(Vector3f.ZERO);
            packet.setLoopCount(1);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                // loop count, then an absent server sound handle
                assertTrue(ByteBufUtil.hexDump(buffer).endsWith("0200"), name);
            } finally {
                buffer.release();
            }
        }
    }
}
