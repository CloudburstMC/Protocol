package org.cloudburstmc.protocol.bedrock.transformer;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.common.util.DefinitionUtils;

public class BlockDefinitionTransformer implements EntityDataTransformer<Integer, BlockDefinition> {

    @Override
    public Integer serialize(BedrockCodecHelper helper, EntityDataMap map, BlockDefinition value) {
        if (helper.getBlockDefinitions() == null) {
            return value.getRuntimeId();
        }

        // Make sure definition is present in known block registry
        return DefinitionUtils.checkDefinition(helper.getBlockDefinitions(), value).getRuntimeId();
    }

    @Override
    public BlockDefinition deserialize(BedrockCodecHelper helper, EntityDataMap map, Integer value) {
        if (helper.getBlockDefinitions() == null) {
            return null;
        }
        return helper.getBlockDefinitions().getDefinition(value);
    }
}
