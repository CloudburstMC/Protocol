package org.cloudburstmc.protocol.bedrock.wrapper;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BedrockWrapperSerializers {
    private static final Int2ObjectMap<BedrockWrapperSerializer> SERIALIZERS = new Int2ObjectOpenHashMap<>();

    static {
        SERIALIZERS.put(7, BedrockWrapperSerializerV7.INSTANCE);
        SERIALIZERS.put(8, BedrockWrapperSerializerV8.INSTANCE);
        SERIALIZERS.put(9, BedrockWrapperSerializerV9_10.V9);
        SERIALIZERS.put(10, BedrockWrapperSerializerV9_10.V10);
        SERIALIZERS.defaultReturnValue(BedrockWrapperSerializerV9_10.V9);
    }

    public static BedrockWrapperSerializer getSerializer(int protocolVersion) {
        return SERIALIZERS.get(protocolVersion);
    }
}
