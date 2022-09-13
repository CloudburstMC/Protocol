package com.nukkitx.protocol.bedrock.wrapper;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class BedrockWrapperSerializers {
    private static final Int2ObjectMap<Supplier<BedrockWrapperSerializer>> SERIALIZERS = new Int2ObjectOpenHashMap<>();

    static {
        SERIALIZERS.put(7, () -> BedrockWrapperSerializerV7.INSTANCE);
        SERIALIZERS.put(8, () -> BedrockWrapperSerializerV8.INSTANCE);
        SERIALIZERS.put(9, () -> BedrockWrapperSerializerV9_10.V9);
        SERIALIZERS.put(10, () -> BedrockWrapperSerializerV9_10.V10);
        SERIALIZERS.put(11, BedrockWrapperSerializerV11::new);
        SERIALIZERS.defaultReturnValue(null);
    }

    public static BedrockWrapperSerializer getSerializer(int protocolVersion) {
        Supplier<BedrockWrapperSerializer> factory = SERIALIZERS.get(protocolVersion);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported RakNet version: " + protocolVersion);
        }
        return SERIALIZERS.get(protocolVersion).get();
    }
}
