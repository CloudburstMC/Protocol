package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.BiomeDefinitionListSerializer_v800;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class BiomeDefinitionListSerializer_v827 extends BiomeDefinitionListSerializer_v800 {

    public static final BiomeDefinitionListSerializer_v827 INSTANCE = new BiomeDefinitionListSerializer_v827();

    @Override
    protected void writeDefinitionId(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition) {
        if (definition.getId() == null) {
            buffer.writeShortLE(-1); // Vanilla biomes don't contain ID field
        } else {
            buffer.writeShortLE(definition.getId());
        }
    }

    @Override
    protected Integer readDefinitionId(ByteBuf buffer, BedrockCodecHelper helper) {
        int id = buffer.readShortLE();
        return id != -1 ? id : null;
    }
}
