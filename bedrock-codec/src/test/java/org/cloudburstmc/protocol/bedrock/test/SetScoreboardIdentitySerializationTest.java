package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v1001.Bedrock_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SetScoreboardIdentitySerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v291.CODEC, Bedrock_v1001.CODEC};
    private static final int PACKET_ID = 112;

    // StartGame unique id of the player in BDS 1.26.x captures
    private static final long PLAYER_UNIQUE_ID = -4294967295L;

    // An added identity carries the player's unique id as a signed varint64, not a UUID
    @Test
    void addedEntryCarriesPlayerUniqueId() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            SetScoreboardIdentityPacket packet = new SetScoreboardIdentityPacket();
            packet.setAction(SetScoreboardIdentityPacket.Action.ADD);
            packet.getEntries().add(new SetScoreboardIdentityPacket.Entry(7, PLAYER_UNIQUE_ID));

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                assertEquals("00010efdffffff1f", ByteBufUtil.hexDump(buffer), name);
                SetScoreboardIdentityPacket decoded = (SetScoreboardIdentityPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
                assertEquals(PLAYER_UNIQUE_ID, decoded.getEntries().get(0).getPlayerId(), name);
                assertFalse(buffer.isReadable(), name + " left bytes unread");
            } finally {
                buffer.release();
            }
        }
    }
}
