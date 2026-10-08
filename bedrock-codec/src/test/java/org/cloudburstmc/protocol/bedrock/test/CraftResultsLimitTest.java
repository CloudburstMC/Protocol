package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v407.Bedrock_v407;
import org.cloudburstmc.protocol.bedrock.codec.v431.Bedrock_v431;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftResultsDeprecatedAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CraftResultsLimitTest {

    private static BedrockCodec codec(int version) {
        switch (version) {
            case 407:
                return Bedrock_v407.CODEC;
            case 431:
                return Bedrock_v431.CODEC;
            default:
                return Bedrock_v2193.CODEC;
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {407, 431, 2193})
    void resultsOverTheLimitAreRefused(int version) {
        BedrockCodec codec = codec(version);
        BedrockCodecHelper helper = codec.createHelper();
        int limit = helper.getEncodingSettings().maxCraftResults();

        // The result count is the byte before the craft count, as both lists are empty
        byte[] once = encode(codec, helper, new ItemData[0], 1);
        byte[] twice = encode(codec, helper, new ItemData[0], 2);
        int crafts = 0;
        while (once[crafts] == twice[crafts]) {
            crafts++;
        }
        ByteBuf body = Unpooled.buffer().writeBytes(once, 0, crafts - 1);
        VarInts.writeUnsignedInt(body, limit + 1);

        try {
            Throwable cause = assertThrows(Exception.class, () -> decode(codec, helper, body));
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            assertTrue(cause instanceof IllegalArgumentException && cause.getMessage().endsWith("maximum is " + limit),
                    cause.toString());
        } finally {
            body.release();
        }
    }

    @Test
    void resultsAtTheLimitAreRead() {
        BedrockCodec codec = Bedrock_v2193.CODEC;
        BedrockCodecHelper helper = codec.createHelper();
        ItemData[] items = new ItemData[helper.getEncodingSettings().maxCraftResults()];
        Arrays.fill(items, ItemData.AIR);

        ByteBuf body = Unpooled.wrappedBuffer(encode(codec, helper, items, 1));
        try {
            ItemStackRequestPacket packet = decode(codec, helper, body);
            ItemStackRequestAction action = packet.getRequests().get(0).getActions()[0];
            assertEquals(items.length, ((CraftResultsDeprecatedAction) action).getResultItems().length);
        } finally {
            body.release();
        }
    }

    private static ItemStackRequestPacket decode(BedrockCodec codec, BedrockCodecHelper helper, ByteBuf body) {
        int id = codec.getPacketDefinition(ItemStackRequestPacket.class).getId();
        return (ItemStackRequestPacket) codec.tryDecode(helper, body, id, PacketRecipient.SERVER);
    }

    private static byte[] encode(BedrockCodec codec, BedrockCodecHelper helper, ItemData[] items, int crafts) {
        ItemStackRequestPacket packet = new ItemStackRequestPacket();
        packet.getRequests().add(new ItemStackRequest(0, new ItemStackRequestAction[]{
                new CraftResultsDeprecatedAction(items, crafts)}, new String[0]));
        ByteBuf buf = Unpooled.buffer();
        try {
            codec.tryEncode(helper, buf, packet);
            return ByteBufUtil.getBytes(buf);
        } finally {
            buf.release();
        }
    }
}
