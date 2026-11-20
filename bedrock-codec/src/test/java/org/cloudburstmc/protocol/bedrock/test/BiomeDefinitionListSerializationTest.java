package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v975.Bedrock_v975;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionChunkGenData;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitions;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeSurfaceBuilderData;
import org.cloudburstmc.protocol.bedrock.packet.BiomeDefinitionListPacket;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BiomeDefinitionListSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v975.CODEC, Bedrock_v2193.CODEC};
    private static final int PACKET_ID = 122;

    // The surface and subsurface builders are optional, as the reader already expected
    @Test
    void surfaceBuildersRoundTrip() {
        BiomeSurfaceBuilderData surface = new BiomeSurfaceBuilderData(null, true, false, false, false, null, null, null);
        BiomeDefinitionChunkGenData chunkGen = new BiomeDefinitionChunkGenData(null, null, null, null, null,
                false, false, false, false, null, null, null, null, null, null, null, surface, null);
        BiomeDefinitionData biome = new BiomeDefinitionData(30000, 0.8f, 0.4f, 0f, 0.1f, 0.2f,
                new Color(0xFF60B7FF, true), true, Collections.singletonList("overworld"), chunkGen);

        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            BiomeDefinitionListPacket packet = new BiomeDefinitionListPacket();
            packet.setBiomes(new BiomeDefinitions(Collections.singletonMap("test:biome", biome)));

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                BiomeDefinitionListPacket decoded = (BiomeDefinitionListPacket) codec.tryDecode(
                        codec.createHelper(), buffer, PACKET_ID, PacketRecipient.CLIENT);
                assertFalse(buffer.isReadable(), name + " left bytes unread");
                BiomeDefinitionChunkGenData decodedChunkGen = decoded.getBiomes().getDefinitions().get("test:biome").getChunkGenData();
                assertEquals(surface, decodedChunkGen.getSurfaceBuilderData(), name);
                assertEquals(null, decodedChunkGen.getSubsurfaceBuilderData(), name);
            } finally {
                buffer.release();
            }
        }
    }
}
