package org.cloudburstmc.protocol.bedrock.transformer;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.defintions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

public class BlockDefinitionTransformer implements EntityDataTransformer<Integer, BlockDefinition> {

    @Override
    public Integer serialize(BedrockCodecHelper helper, EntityDataMap map, BlockDefinition value) {
        return value.getRuntimeId();
    }

    @Override
    public BlockDefinition deserialize(BedrockCodecHelper helper, EntityDataMap map, Integer value) {
        if (helper.getBlockDefinitions() == null) {
            return null;
        }
        return helper.getBlockDefinitions().getDefinition(value);
    }
}
