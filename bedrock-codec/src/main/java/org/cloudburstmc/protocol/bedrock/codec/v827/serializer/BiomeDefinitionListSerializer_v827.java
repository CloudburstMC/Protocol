package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.BiomeDefinitionListSerializer_v800;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;
import org.cloudburstmc.protocol.common.util.SequencedHashSet;
import org.cloudburstmc.protocol.common.util.index.Indexed;

import java.util.List;

public class BiomeDefinitionListSerializer_v827 extends BiomeDefinitionListSerializer_v800 {

    public static final BiomeDefinitionListSerializer_v827 INSTANCE = new BiomeDefinitionListSerializer_v827();

    @Override
    protected void writeDefinitionId(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition, SequencedHashSet<String> strings) {
        if (definition.getId() == null) {
            buffer.writeShortLE(-1); // Vanilla biomes don't contain ID field
        } else {
            buffer.writeShortLE(strings.addAndGetIndex(definition.getId()));
        }
    }

    @Override
    protected Indexed<String> readDefinitionId(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        int idIndex = buffer.readShortLE();
        Indexed<String> id = null;
        if (idIndex != -1) {
            id = new Indexed<>(strings, idIndex);
        }
        return id;
    }
}
