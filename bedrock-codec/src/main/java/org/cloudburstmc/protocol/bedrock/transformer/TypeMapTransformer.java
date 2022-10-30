package org.cloudburstmc.protocol.bedrock.transformer;

import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.common.util.TypeMap;

@RequiredArgsConstructor
public final class TypeMapTransformer<T> implements EntityDataTransformer<Integer, T> {
    private final TypeMap<T> typeMap;

    @Override
    public Integer serialize(BedrockCodecHelper helper, EntityDataMap map, T value) {
        return typeMap.getId(value);
    }

    @Override
    public T deserialize(BedrockCodecHelper helper, EntityDataMap map, Integer value) {
        return typeMap.getType(value);
    }
}
