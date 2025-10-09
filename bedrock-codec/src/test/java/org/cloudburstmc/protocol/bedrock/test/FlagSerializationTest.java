package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.Bedrock_v776;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.junit.jupiter.api.AssertionFailureBuilder;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.EnumMap;


public class FlagSerializationTest {
    private static final BedrockCodecHelper CODEC_HELPER = Bedrock_v776.CODEC.createHelper();

    private static final TypeMap<EntityFlag> ENTITY_FLAGS;

    static {
        try {
            Field field = Bedrock_v776.class.getDeclaredField("ENTITY_FLAGS");
            field.setAccessible(true);
            ENTITY_FLAGS = (TypeMap<EntityFlag>) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static final FlagTransformer FLAGS_TRANSFORMER = new FlagTransformer(ENTITY_FLAGS, 0);
    private static final FlagTransformer FLAGS_2_TRANSFORMER = new FlagTransformer(ENTITY_FLAGS, 1);

    @Test
    public void testFlagSerialization() {
        EntityDataMap dataMap = new EntityDataMap();
        dataMap.setFlag(EntityFlag.BLOCKING, true); // FLAGS_2

        testSerializationRoundTrip(dataMap);
        verifyTransformerOutput(dataMap, false, true);

        dataMap = new EntityDataMap();
        dataMap.setFlag(EntityFlag.HAS_GRAVITY, true); // FLAGS

        testSerializationRoundTrip(dataMap);
        verifyTransformerOutput(dataMap, true, false);

        dataMap = new EntityDataMap();
        dataMap.setFlag(EntityFlag.HAS_GRAVITY, true); // FLAGS
        dataMap.setFlag(EntityFlag.BLOCKING, true); // FLAGS_2

        testSerializationRoundTrip(dataMap);
        verifyTransformerOutput(dataMap, true, true);

        dataMap = new EntityDataMap();
        dataMap.setFlag(EntityFlag.HAS_GRAVITY, false); // FLAGS
        dataMap.setFlag(EntityFlag.BLOCKING, true); // FLAGS_2

        testSerializationRoundTrip(dataMap);
        verifyTransformerOutput(dataMap, true, true);
    }

    private void verifyTransformerOutput(EntityDataMap dataMap, boolean shouldHaveFlags, boolean shouldHaveFlags2) {
        Long serializedFlags = FLAGS_TRANSFORMER.serialize(CODEC_HELPER, dataMap, dataMap.getFlags());
        if (shouldHaveFlags && serializedFlags == null) {
            AssertionFailureBuilder.assertionFailure()
                    .message("FLAGS")
                    .expected("non-null value")
                    .actual(null)
                    .buildAndThrow();
        } else if (!shouldHaveFlags && serializedFlags != null) {
            AssertionFailureBuilder.assertionFailure()
                    .message("FLAGS")
                    .expected(null)
                    .actual(serializedFlags)
                    .buildAndThrow();
        }

        Long serializedFlags2 = FLAGS_2_TRANSFORMER.serialize(CODEC_HELPER, dataMap, dataMap.getFlags());
        if (shouldHaveFlags2 && serializedFlags2 == null) {
            AssertionFailureBuilder.assertionFailure()
                    .message("FLAGS_2")
                    .expected("non-null value")
                    .actual(null)
                    .buildAndThrow();
        } else if (!shouldHaveFlags2 && serializedFlags2 != null) {
            AssertionFailureBuilder.assertionFailure()
                    .message("FLAGS_2")
                    .expected(null)
                    .actual(serializedFlags2)
                    .buildAndThrow();
        }
    }

    private void testSerializationRoundTrip(EntityDataMap originalDataMap) {
        ByteBuf buffer = Unpooled.buffer();
        CODEC_HELPER.writeEntityData(buffer, originalDataMap);

        EntityDataMap deserializedDataMap = new EntityDataMap();
        CODEC_HELPER.readEntityData(buffer, deserializedDataMap);

        assertFlagsMatch(originalDataMap, deserializedDataMap);
    }

    private void assertFlagsMatch(EntityDataMap expected, EntityDataMap actual) {
        EnumMap<EntityFlag, Boolean> expectedFlags = expected.getFlags();
        EnumMap<EntityFlag, Boolean> actualFlags = actual.getFlags();
        for (EntityFlag flag : EntityFlag.values()) {
            Boolean actualValue = actualFlags.get(flag);
            Boolean expectedValue = expectedFlags.get(flag);
            if (expectedValue == null && Boolean.TRUE.equals(actualValue)) {
                AssertionFailureBuilder.assertionFailure()
                        .message("Flag " + flag + " should not be set")
                        .expected(null)
                        .actual(true)
                        .buildAndThrow();
            }
        }
    }
}