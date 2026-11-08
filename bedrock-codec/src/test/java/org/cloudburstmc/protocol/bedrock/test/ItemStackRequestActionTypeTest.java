package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168_hotfix4;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v2225.Bedrock_v2225;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftCreativeAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftNonImplementedAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftResultsDeprecatedAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

// The decoder skips the enum value, so the bytes are read directly
public class ItemStackRequestActionTypeTest {

    // {variant index, enum value} of CraftCreative, CraftResultsDeprecated, CraftNonImplemented
    private static final int[][] BEFORE_1_26_60 = {{12, 14}, {17, 19}, {16, 18}};
    private static final int[][] SINCE_1_26_60 = {{12, 14}, {18, 20}, {17, 19}};

    static Stream<Arguments> codecs() {
        return Stream.of(
                Arguments.of(Named.of("v2168", Bedrock_v2168.CODEC), BEFORE_1_26_60),
                Arguments.of(Named.of("v2168 hotfix 4", Bedrock_v2168_hotfix4.CODEC), BEFORE_1_26_60),
                Arguments.of(Named.of("v2169", Bedrock_v2169.CODEC), BEFORE_1_26_60),
                Arguments.of(Named.of("v2193", Bedrock_v2193.CODEC), BEFORE_1_26_60),
                Arguments.of(Named.of("v2225", Bedrock_v2225.CODEC), SINCE_1_26_60)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("codecs")
    void actionsStartWithTheVariantIndexAndEnumValueOfTheVersion(BedrockCodec codec, int[][] expected) {
        ItemStackRequestPacket packet = new ItemStackRequestPacket();
        packet.getRequests().add(new ItemStackRequest(0, new ItemStackRequestAction[]{
                new CraftCreativeAction(77, 1),
                new CraftResultsDeprecatedAction(new ItemData[0], 1),
                new CraftNonImplementedAction()
        }, new String[0], null));

        ByteBuf buf = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), buf, packet);
            assertEquals(1, VarInts.readUnsignedInt(buf), "request count");
            assertEquals(0, VarInts.readInt(buf), "request id");
            assertEquals(3, VarInts.readUnsignedInt(buf), "action count");

            assertActionStart(buf, expected[0], "CraftCreative");
            assertEquals(77, VarInts.readUnsignedInt(buf), "creative item network id");
            assertEquals(1, buf.readUnsignedByte(), "creative crafts");

            assertActionStart(buf, expected[1], "CraftResultsDeprecated");
            assertEquals(0, VarInts.readUnsignedInt(buf), "result item count");
            assertEquals(1, buf.readUnsignedByte(), "times crafted");

            assertActionStart(buf, expected[2], "CraftNonImplemented");

            assertEquals(0, VarInts.readUnsignedInt(buf), "filter string count");
            assertEquals(-1, buf.readIntLE(), "text processing origin");
            assertEquals(0, buf.readableBytes(), "trailing bytes");
        } finally {
            buf.release();
        }
    }

    private static void assertActionStart(ByteBuf buf, int[] expected, String action) {
        assertEquals(expected[0], VarInts.readUnsignedInt(buf), action + " variant index");
        assertEquals(expected[1], buf.readUnsignedByte(), action + " enum value");
    }
}
