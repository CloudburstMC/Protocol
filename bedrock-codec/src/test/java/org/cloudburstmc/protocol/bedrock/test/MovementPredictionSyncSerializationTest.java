package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v776.Bedrock_v776;
import org.cloudburstmc.protocol.bedrock.codec.v786.Bedrock_v786;
import org.cloudburstmc.protocol.bedrock.codec.v975.Bedrock_v975;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.MovementPredictionSyncPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MovementPredictionSyncSerializationTest {
    private static final BedrockCodec[] CODECS = {
            Bedrock_v776.CODEC, Bedrock_v786.CODEC, Bedrock_v975.CODEC, Bedrock_v2193.CODEC
    };
    private static final int PACKET_ID = 322;

    // StartGame unique id of the player in BDS 1.26.x captures, where the old unsigned read gave its zigzag form
    private static final long CAPTURED_UNIQUE_ID = -4294967295L;
    private static final long CAPTURED_UNSIGNED_READ = 8589934589L;

    @Test
    void entityIdIsTheSignedUniqueId() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            MovementPredictionSyncPacket packet = new MovementPredictionSyncPacket();
            packet.setBoundingBox(Vector3f.from(0.6f, 1.8f, 1f));
            packet.setUniqueEntityId(CAPTURED_UNIQUE_ID);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);

                ByteBuf unsigned = Unpooled.buffer();
                VarInts.writeUnsignedLong(unsigned, CAPTURED_UNSIGNED_READ);
                int flying = codec.getProtocolVersion() >= 786 ? 1 : 0;
                int idStart = buffer.writerIndex() - flying - unsigned.readableBytes();
                assertEquals(unsigned, buffer.slice(idStart, unsigned.readableBytes()), name + " bytes BDS sends");
                unsigned.release();

                MovementPredictionSyncPacket decoded = (MovementPredictionSyncPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.SERVER);
                assertEquals(CAPTURED_UNIQUE_ID, decoded.getUniqueEntityId(), name);
                assertFalse(buffer.isReadable(), name + " left bytes unread");
            } finally {
                buffer.release();
            }
        }
    }
}
