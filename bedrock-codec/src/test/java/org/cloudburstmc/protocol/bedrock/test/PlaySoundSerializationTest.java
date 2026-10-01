package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaySoundSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v2168.CODEC, Bedrock_v2169.CODEC};

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
