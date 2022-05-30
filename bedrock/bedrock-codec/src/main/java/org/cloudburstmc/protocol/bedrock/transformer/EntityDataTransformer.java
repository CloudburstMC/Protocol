package org.cloudburstmc.protocol.bedrock.transformer;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

public interface EntityDataTransformer<S, D> {

    EntityDataTransformer<?, ?> IDENTITY = new EntityDataTransformer<Object, Object>() {
        @Override
        public Object serialize(BedrockCodecHelper helper, EntityDataMap map, Object value) {
            return value;
        }

        @Override
        public Object deserialize(BedrockCodecHelper helper, EntityDataMap map, Object value) {
            return value;
        }
    };

    @SuppressWarnings("unchecked")
    static <S, D> EntityDataTransformer<S, D> identity() {
        return (EntityDataTransformer<S, D>) IDENTITY;
    }

    S serialize(BedrockCodecHelper helper, EntityDataMap map, D value);

    D deserialize(BedrockCodecHelper helper, EntityDataMap map, S value);
}
