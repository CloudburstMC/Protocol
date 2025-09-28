package org.cloudburstmc.protocol.bedrock.transformer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringTransformer implements EntityDataTransformer<String, CharSequence> {

    public static final StringTransformer INSTANCE = new StringTransformer();

    @Override
    public String serialize(BedrockCodecHelper helper, EntityDataMap map, CharSequence value) {
        // We should always support string values
        if (value instanceof String) {
            return (String) value;
        } else {
            return helper.getTextConverter().serialize(value);
        }
    }

    @Override
    public CharSequence deserialize(BedrockCodecHelper helper, EntityDataMap map, String value) {
        return helper.getTextConverter().deserialize(value);
    }
}
