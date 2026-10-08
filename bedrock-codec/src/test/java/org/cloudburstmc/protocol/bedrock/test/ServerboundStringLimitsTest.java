package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureAnimationMode;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureMirror;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRotation;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureTemplateRequestOperation;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BookEditPacket;
import org.cloudburstmc.protocol.bedrock.packet.CommandRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.PartyChangedPacket;
import org.cloudburstmc.protocol.bedrock.packet.PartyDestinationCookieResponsePacket;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDataStorePacket;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundPackSettingChangePacket;
import org.cloudburstmc.protocol.bedrock.packet.StructureTemplateDataRequestPacket;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Mojang's published limits for strings that clients send
public class ServerboundStringLimitsTest {

    private static final BedrockCodec CODEC = Bedrock_v2193.CODEC;

    static Stream<Arguments> fields() {
        return Stream.of(
                field("command", 1000, value -> {
                    CommandRequestPacket packet = new CommandRequestPacket();
                    packet.setCommand(value);
                    packet.setCommandOriginData(new CommandOriginData(CommandOriginType.PLAYER, new UUID(0, 0), "", 0));
                    return packet;
                }, packet -> ((CommandRequestPacket) packet).getCommand()),
                field("book page text", 768, value -> page(value, ""),
                        packet -> ((BookEditPacket) packet).getText().toString()),
                field("book photo name", 768, value -> page("", value),
                        packet -> ((BookEditPacket) packet).getPhotoName()),
                field("book title", 768, value -> sign(value, "", ""),
                        packet -> ((BookEditPacket) packet).getTitle().toString()),
                field("book author", 768, value -> sign("", value, ""),
                        packet -> ((BookEditPacket) packet).getAuthor().toString()),
                field("book xuid", 768, value -> sign("", "", value),
                        packet -> ((BookEditPacket) packet).getXuid()),
                field("structure name", 256, value -> structure(value, ""),
                        packet -> ((StructureTemplateDataRequestPacket) packet).getName()),
                field("structure palette name", 256, value -> structure("", value),
                        packet -> ((StructureTemplateDataRequestPacket) packet).getSettings().getPaletteName()),
                field("item stack request filter string", 1000, value -> {
                    ItemStackRequestPacket packet = new ItemStackRequestPacket();
                    packet.getRequests().add(new ItemStackRequest(0, new ItemStackRequestAction[0], new String[]{value}));
                    return packet;
                }, packet -> ((ItemStackRequestPacket) packet).getRequests().get(0).getFilterStrings()[0]),
                field("pack setting name", 128, value -> {
                    ServerboundPackSettingChangePacket packet = new ServerboundPackSettingChangePacket();
                    packet.setPackId(new UUID(0, 0));
                    packet.setPackSettingName(value);
                    packet.setPackSettingValue(0f);
                    return packet;
                }, packet -> ((ServerboundPackSettingChangePacket) packet).getPackSettingName()),
                field("data store name", 1000, value -> dataStore(value, "", "", ""),
                        packet -> ((ServerboundDataStorePacket) packet).getUpdate().getDataStoreName()),
                field("data store property", 1000, value -> dataStore("", value, "", ""),
                        packet -> ((ServerboundDataStorePacket) packet).getUpdate().getProperty()),
                field("data store path", 1000, value -> dataStore("", "", value, ""),
                        packet -> ((ServerboundDataStorePacket) packet).getUpdate().getPath()),
                field("data store string", 5000, value -> dataStore("", "", "", value),
                        packet -> ((ServerboundDataStorePacket) packet).getUpdate().getData()),
                field("party id", 49, value -> {
                    PartyChangedPacket packet = new PartyChangedPacket();
                    packet.setParty(new PartyChangedPacket.PartyInfo(value, false));
                    return packet;
                }, packet -> ((PartyChangedPacket) packet).getParty().getPartyId()),
                field("party destination cookie", 2048, value -> {
                    PartyDestinationCookieResponsePacket packet = new PartyDestinationCookieResponsePacket();
                    packet.setCookie(value);
                    return packet;
                }, packet -> ((PartyDestinationCookieResponsePacket) packet).getCookie())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("fields")
    void stringsAtTheLimitAreRead(String name, int limit, Function<String, BedrockPacket> build,
                                  Function<BedrockPacket, Object> read) {
        String value = repeat(limit);
        assertEquals(value, read.apply(roundTrip(build.apply(value))));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("fields")
    void stringsOverTheLimitAreRefused(String name, int limit, Function<String, BedrockPacket> build,
                                       Function<BedrockPacket, Object> read) {
        BedrockPacket packet = build.apply(repeat(limit + 1));
        Throwable cause = assertThrows(Exception.class, () -> roundTrip(packet));
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        assertTrue(cause instanceof IllegalArgumentException && cause.getMessage().endsWith("maximum is " + limit),
                cause.toString());
    }

    private static Arguments field(String name, int limit, Function<String, BedrockPacket> build,
                                   Function<BedrockPacket, Object> read) {
        return Arguments.of(name, limit, build, read);
    }

    private static BedrockPacket roundTrip(BedrockPacket packet) {
        BedrockCodecHelper helper = CODEC.createHelper();
        ByteBuf buf = Unpooled.buffer();
        try {
            CODEC.tryEncode(helper, buf, packet);
            int id = CODEC.getPacketDefinition(packet.getClass()).getId();
            return CODEC.tryDecode(helper, buf, id, PacketRecipient.SERVER);
        } finally {
            buf.release();
        }
    }

    private static String repeat(int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

    private static BookEditPacket page(String text, String photoName) {
        BookEditPacket packet = new BookEditPacket();
        packet.setAction(BookEditPacket.Action.REPLACE_PAGE);
        packet.setText(text);
        packet.setPhotoName(photoName);
        return packet;
    }

    private static BookEditPacket sign(String title, String author, String xuid) {
        BookEditPacket packet = new BookEditPacket();
        packet.setAction(BookEditPacket.Action.SIGN_BOOK);
        packet.setTitle(title);
        packet.setAuthor(author);
        packet.setXuid(xuid);
        return packet;
    }

    private static StructureTemplateDataRequestPacket structure(String name, String paletteName) {
        StructureTemplateDataRequestPacket packet = new StructureTemplateDataRequestPacket();
        packet.setName(name);
        packet.setPosition(Vector3i.ZERO);
        packet.setSettings(new StructureSettings(paletteName, false, false, false, Vector3i.ZERO, Vector3i.ZERO, 0,
                StructureRotation.NONE, StructureMirror.NONE, StructureAnimationMode.NONE, 0, 0, 0, Vector3f.ZERO));
        packet.setOperation(StructureTemplateRequestOperation.NONE);
        return packet;
    }

    private static ServerboundDataStorePacket dataStore(String name, String property, String path, String data) {
        ServerboundDataStorePacket packet = new ServerboundDataStorePacket();
        packet.getUpdate().setDataStoreName(name);
        packet.getUpdate().setProperty(property);
        packet.getUpdate().setPath(path);
        packet.getUpdate().setData(data);
        return packet;
    }
}
