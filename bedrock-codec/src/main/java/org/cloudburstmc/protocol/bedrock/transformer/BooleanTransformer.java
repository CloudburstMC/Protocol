package org.cloudburstmc.protocol.bedrock.transformer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BooleanTransformer implements EntityDataTransformer<Byte, Boolean> {

    public static final BooleanTransformer INSTANCE = new BooleanTransformer();

    @Override
    public Byte serialize(BedrockCodecHelper helper, EntityDataMap map, Boolean value) {
        return (byte) (value == Boolean.TRUE ? 1 : 0);
    }

    @Override
    public Boolean deserialize(BedrockCodecHelper helper, EntityDataMap map, Byte value) {
        return value == 1 ? Boolean.TRUE : Boolean.FALSE;
    }
}
