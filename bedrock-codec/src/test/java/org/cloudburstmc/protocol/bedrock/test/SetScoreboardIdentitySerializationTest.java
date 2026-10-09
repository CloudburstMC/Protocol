package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v1001.Bedrock_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.codec.v662.Bedrock_v662;
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766;
import org.cloudburstmc.protocol.bedrock.codec.v860.Bedrock_v860;
import org.cloudburstmc.protocol.bedrock.codec.v924.Bedrock_v924;
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

    // Sent by BDS when a player joins for the first time while a fake player with their name has a score:
    // the fake player becomes the player. 1.20.70 to 1.26.30, then 1.26.45 with a presence byte
    @Test
    void capturedRegisterDecodes() throws Exception {
        assertCapturedRegister(Bedrock_v662.CODEC, "000102fdffffff1f");
        assertCapturedRegister(Bedrock_v766.CODEC, "000102fdffffff1f");
        assertCapturedRegister(Bedrock_v860.CODEC, "000102fdffffff1f");
        assertCapturedRegister(Bedrock_v924.CODEC, "000102fdffffff1f");
        assertCapturedRegister(Bedrock_v1001.CODEC, "000102fdffffff1f");
        assertCapturedRegister(Bedrock_v2169.CODEC, "00010201fdffffff1f");
    }

    private static void assertCapturedRegister(BedrockCodec codec, String hex) throws Exception {
        String name = "v" + codec.getProtocolVersion();
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        SetScoreboardIdentityPacket packet = (SetScoreboardIdentityPacket) codec.tryDecode(
                codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
        assertFalse(buffer.isReadable(), name + " left bytes unread");
        assertEquals(SetScoreboardIdentityPacket.Action.ADD, packet.getAction(), name);
        assertEquals(1, packet.getEntries().get(0).getScoreboardId(), name);
        assertEquals(PLAYER_UNIQUE_ID, packet.getEntries().get(0).getPlayerId(), name);

        ByteBuf encoded = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), encoded, packet);
            assertEquals(hex, ByteBufUtil.hexDump(encoded), name);
        } finally {
            encoded.release();
        }
    }

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
