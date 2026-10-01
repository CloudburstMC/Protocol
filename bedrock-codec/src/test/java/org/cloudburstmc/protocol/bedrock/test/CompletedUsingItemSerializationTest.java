package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v388.Bedrock_v388;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemUseType;
import org.cloudburstmc.protocol.bedrock.packet.CompletedUsingItemPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletedUsingItemSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v388.CODEC, Bedrock_v2193.CODEC};
    private static final int PACKET_ID = 142;

    // The item id is a signed 16 bit integer, negative for block items
    @Test
    void negativeItemIdRoundTrips() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            CompletedUsingItemPacket packet = new CompletedUsingItemPacket();
            packet.setItemId(-5);
            packet.setType(ItemUseType.EAT);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                CompletedUsingItemPacket decoded = (CompletedUsingItemPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
                assertEquals(-5, decoded.getItemId(), name);
            } finally {
                buffer.release();
            }
        }
    }
}
