package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v1001.Bedrock_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168_hotfix4;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v860.Bedrock_v860;
import org.cloudburstmc.protocol.bedrock.codec.v898.Bedrock_v898;
import org.cloudburstmc.protocol.bedrock.codec.v924.Bedrock_v924;
import org.cloudburstmc.protocol.bedrock.codec.v944.Bedrock_v944;
import org.cloudburstmc.protocol.bedrock.codec.v975.Bedrock_v975;
import org.cloudburstmc.protocol.bedrock.data.BlockInteractionType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.event.AchievementAwardedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.BellUsedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.CauldronInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.CauldronUsedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.ComposterInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.EntityInteractEventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.bedrock.data.event.MobKilledEventData;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EventSerializationTest {
    // Every codec since the payloads became fixed width
    private static final BedrockCodec[] CODECS = {
            Bedrock_v898.CODEC, Bedrock_v924.CODEC, Bedrock_v944.CODEC, Bedrock_v975.CODEC, Bedrock_v1001.CODEC,
            Bedrock_v2168.CODEC, Bedrock_v2168_hotfix4.CODEC, Bedrock_v2169.CODEC, Bedrock_v2193.CODEC
    };
    private static final int EVENT_PACKET_ID = 65;
    private static final long PLAYER_ID = -2211908157439L;

    // Recovering bone meal from a composter while holding a potato, captured from BDS
    private static final String RECOVERED_BONEMEAL = "fdffffffdf800120010b141801";

    // Payloads laid out by the field types in Mojang's protocol docs
    private static final Map<EventData, String> PAYLOADS = new LinkedHashMap<>();

    static {
        PAYLOADS.put(new AchievementAwardedEventData(200), "c8");
        PAYLOADS.put(new EntityInteractEventData(-12, 200, 12, 3, 250), "17c81806fa");
        PAYLOADS.put(new CauldronUsedEventData(-300, 0xFF3F76E4, 3), "e4edfdf90fd4fe0300");
        PAYLOADS.put(new CauldronInteractEventData(BlockInteractionType.DYE_ITEM, -2), "10feff");
        PAYLOADS.put(new ComposterInteractEventData(BlockInteractionType.COMPOST_ITEM_PLACE, 32000), "13007d");
        PAYLOADS.put(new BellUsedEventData(-1), "ffff");
    }

    // Captured from BDS, each decoded with the codec of the version it came from
    private static final Object[][] CAPTURES = {
            // 1.21.124.2, still var ints
            {Bedrock_v860.CODEC, "fdffffff1f2001268705", new ComposterInteractEventData(BlockInteractionType.COMPOST_ITEM_PLACE, -324)},
            {Bedrock_v860.CODEC, "fdffffff1f2001288705", new ComposterInteractEventData(BlockInteractionType.RECOVERED_BONEMEAL, -324)},
            {Bedrock_v860.CODEC, "fdffffff1f1e011a8606", new CauldronInteractEventData(BlockInteractionType.FILLED, 387)},
            {Bedrock_v860.CODEC, "fdffffff1f1e011c8a07", new CauldronInteractEventData(BlockInteractionType.EMPTIED, 453)},
            {Bedrock_v860.CODEC, "fdffffff1f020183f9ffff1f0a1a0000", new EntityInteractEventData(-4294966850L, 5, 13, 0, 0)},
            {Bedrock_v860.CODEC, "fdffffff1f0201fff8ffff1f0c160000", new EntityInteractEventData(-4294966848L, 6, 11, 0, 0)},
            // 1.21.132.3
            {Bedrock_v898.CODEC, "fdffffff1f20010b13dffc", new ComposterInteractEventData(BlockInteractionType.COMPOST_ITEM_PLACE, -801)},
            {Bedrock_v898.CODEC, "fdffffff1f20010b14dffc", new ComposterInteractEventData(BlockInteractionType.RECOVERED_BONEMEAL, -801)},
            {Bedrock_v898.CODEC, "fdffffff1f0a0105002a000600", new CauldronUsedEventData(42, 0, 6)},
            {Bedrock_v898.CODEC, "fdffffff1f1e010a0dcb01", new CauldronInteractEventData(BlockInteractionType.FILLED, 459)},
            {Bedrock_v898.CODEC, "fdffffff1f1e010a0e8801", new CauldronInteractEventData(BlockInteractionType.EMPTIED, 392)},
            {Bedrock_v898.CODEC, "fdffffff1f020101bfffffff1f051a0000", new EntityInteractEventData(-4294967264L, 5, 13, 0, 0)},
            {Bedrock_v898.CODEC, "fdffffff1f020101b3ffffff1f06160000", new EntityInteractEventData(-4294967258L, 6, 11, 0, 0)},
            {Bedrock_v898.CODEC, "fdffffff1f080104fdffffff1fc9ffffff1f02040100", new MobKilledEventData(-4294967295L, -4294967269L, 1, 2, -1, "")},
            // 1.26.51.1
            {Bedrock_v2193.CODEC, "b7feffffdf0120010b1328fe", new ComposterInteractEventData(BlockInteractionType.COMPOST_ITEM_PLACE, -472)},
            {Bedrock_v2193.CODEC, "b7feffffdf0120010b1428fe", new ComposterInteractEventData(BlockInteractionType.RECOVERED_BONEMEAL, -472)},
            {Bedrock_v2193.CODEC, "b7feffffdf010a01050014000600", new CauldronUsedEventData(20, 0, 6)},
            {Bedrock_v2193.CODEC, "b7feffffdf011e010a0d6d01", new CauldronInteractEventData(BlockInteractionType.FILLED, 365)},
            {Bedrock_v2193.CODEC, "b7feffffdf011e010a0e6b01", new CauldronInteractEventData(BlockInteractionType.EMPTIED, 363)},
            {Bedrock_v2193.CODEC, "b7feffffdf01020101affdffffdf01051a0007", new EntityInteractEventData(-30064770904L, 5, 13, 0, 7)},
    };

    @Test
    public void testDecodeCapturedEvents() {
        for (Object[] capture : CAPTURES) {
            BedrockCodec codec = (BedrockCodec) capture[0];
            String hex = (String) capture[1];
            String name = "v" + codec.getProtocolVersion() + " " + hex;
            EventPacket packet = assertDoesNotThrow(() -> decode(codec, hex), name);
            assertEquals(capture[2], packet.getEventData(), name);
            assertEquals(hex, encode(codec, packet), name);
        }
    }

    @Test
    public void testDecodeCapturedComposterEvent() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            EventPacket packet = decode(codec, RECOVERED_BONEMEAL);
            assertEquals(PLAYER_ID, packet.getUniqueEntityId(), name);
            assertTrue(packet.isUsePlayerId(), name);
            assertEquals(new ComposterInteractEventData(BlockInteractionType.RECOVERED_BONEMEAL, 280), packet.getEventData(), name);
            assertEquals(RECOVERED_BONEMEAL, encode(codec, packet), name);
        }
    }

    @Test
    public void testFixedWidthPayloads() {
        for (BedrockCodec codec : CODECS) {
            for (Map.Entry<EventData, String> entry : PAYLOADS.entrySet()) {
                EventPacket packet = new EventPacket();
                packet.setUniqueEntityId(PLAYER_ID);
                packet.setUsePlayerId(true);
                packet.setEventData(entry.getKey());

                String wire = header(packet) + entry.getValue();
                String name = "v" + codec.getProtocolVersion() + " " + entry.getKey().getType();
                assertEquals(wire, encode(codec, packet), name);
                assertEquals(entry.getKey(), decode(codec, wire).getEventData(), name);
            }
        }
    }

    private static EventPacket decode(BedrockCodec codec, String hex) {
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        try {
            EventPacket packet = (EventPacket) codec.tryDecode(codec.createHelper(), buffer, EVENT_PACKET_ID, PacketRecipient.CLIENT);
            assertFalse(buffer.isReadable(), "trailing bytes after " + packet.getEventData());
            return packet;
        } finally {
            buffer.release();
        }
    }

    private static String encode(BedrockCodec codec, EventPacket packet) {
        ByteBuf buffer = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), buffer, packet);
            return ByteBufUtil.hexDump(buffer);
        } finally {
            buffer.release();
        }
    }

    private static String header(EventPacket packet) {
        ByteBuf buffer = Unpooled.buffer();
        try {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            VarInts.writeInt(buffer, packet.getEventData().getType().ordinal());
            buffer.writeBoolean(packet.isUsePlayerId());
            VarInts.writeUnsignedInt(buffer, packet.getEventData().getPayloadType());
            return ByteBufUtil.hexDump(buffer);
        } finally {
            buffer.release();
        }
    }
}
