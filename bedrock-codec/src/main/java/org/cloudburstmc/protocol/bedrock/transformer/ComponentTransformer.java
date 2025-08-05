package org.cloudburstmc.protocol.bedrock.transformer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComponentTransformer implements EntityDataTransformer<String, Component> {

    public static final ComponentTransformer INSTANCE = new ComponentTransformer();

    @Override
    public String serialize(BedrockCodecHelper helper, EntityDataMap map, Component value) {
        return helper.getLegacyComponentSerializer().serialize(value);
    }

    @Override
    public Component deserialize(BedrockCodecHelper helper, EntityDataMap map, String value) {
        return helper.getLegacyComponentSerializer().deserialize(value);
    }
}
