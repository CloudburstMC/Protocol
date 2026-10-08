package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtUtils;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v332.Bedrock_v332;
import org.cloudburstmc.protocol.bedrock.codec.v340.Bedrock_v340;
import org.cloudburstmc.protocol.bedrock.data.EncodingSettings;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket;
import org.cloudburstmc.protocol.common.SimpleDefinitionRegistry;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LegacyItemReaderLimitsTest {

    private static final NbtMap TAG = NbtMap.builder().putString("Name", "stick").build();

    private static BedrockCodecHelper helper(BedrockCodec codec, EncodingSettings settings) {
        BedrockCodecHelper helper = codec.createHelper();
        helper.setEncodingSettings(settings);
        helper.setItemDefinitions(SimpleDefinitionRegistry.<ItemDefinition>builder()
                .add(new SimpleItemDefinition("minecraft:stick", 1, false))
                .build());
        return helper;
    }

    private static BedrockCodec codec(int version) {
        return version == 332 ? Bedrock_v332.CODEC : Bedrock_v340.CODEC;
    }

    @ParameterizedTest
    @ValueSource(ints = {332, 340})
    void canPlaceOverTheLimitIsRefused(int version) {
        int limit = EncodingSettings.SERVER.maxListSize();
        assertRefused(version, EncodingSettings.SERVER, "maximum is " + limit, item -> {
            item.writeShortLE(0);
            VarInts.writeInt(item, limit + 1);
            return item;
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {332, 340})
    void canBreakOverTheLimitIsRefused(int version) {
        int limit = EncodingSettings.SERVER.maxListSize();
        assertRefused(version, EncodingSettings.SERVER, "maximum is " + limit, item -> {
            item.writeShortLE(0);
            VarInts.writeInt(item, 0);
            VarInts.writeInt(item, limit + 1);
            return item;
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {332, 340})
    void networkNbtOverTheItemLimitIsRefused(int version) {
        EncodingSettings settings = EncodingSettings.SERVER.toBuilder().maxItemNBTSize(8).build();
        assertRefused(version, settings, "Read size exceeded limit", item -> {
            item.writeShortLE(-1);
            item.writeByte(1);
            write(NbtUtils.createNetworkWriter(new ByteBufOutputStream(item)));
            VarInts.writeInt(item, 0);
            VarInts.writeInt(item, 0);
            return item;
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {332, 340})
    void nbtWithASizeIsRead(int version) {
        ByteBuf nbt = Unpooled.buffer();
        write(NbtUtils.createWriterLE(new ByteBufOutputStream(nbt)));
        ByteBuf body = body(item -> {
            item.writeShortLE(nbt.readableBytes());
            item.writeBytes(nbt);
            VarInts.writeInt(item, 0);
            VarInts.writeInt(item, 0);
            return item;
        });
        try {
            assertEquals(TAG, decode(version, EncodingSettings.SERVER, body).getItem().getTag());
        } finally {
            nbt.release();
            body.release();
        }
    }

    private static void assertRefused(int version, EncodingSettings settings, String message,
                                      Function<ByteBuf, ByteBuf> rest) {
        ByteBuf body = body(rest);
        try {
            Throwable cause = assertThrows(Exception.class, () -> decode(version, settings, body));
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            assertTrue(cause.getMessage() != null && cause.getMessage().contains(message), cause.toString());
        } finally {
            body.release();
        }
    }

    // A MobEquipmentPacket whose item is stick, count 1, followed by what rest writes
    private static ByteBuf body(Function<ByteBuf, ByteBuf> rest) {
        ByteBuf body = Unpooled.buffer();
        VarInts.writeUnsignedLong(body, 1);
        VarInts.writeInt(body, 1);
        VarInts.writeInt(body, 1);
        rest.apply(body);
        return body.writeByte(0).writeByte(0).writeByte(0);
    }

    private static MobEquipmentPacket decode(int version, EncodingSettings settings, ByteBuf body) {
        BedrockCodec codec = codec(version);
        int id = codec.getPacketDefinition(MobEquipmentPacket.class).getId();
        return (MobEquipmentPacket) codec.tryDecode(helper(codec, settings), body, id, PacketRecipient.SERVER);
    }

    private static void write(NBTOutputStream writer) {
        try (NBTOutputStream stream = writer) {
            stream.writeTag(TAG);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
