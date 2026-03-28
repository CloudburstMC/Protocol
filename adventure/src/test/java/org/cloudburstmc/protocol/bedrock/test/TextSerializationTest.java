package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentIteratorType;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.adventure.AdventureTextConverter;
import org.cloudburstmc.protocol.adventure.BedrockComponent;
import org.cloudburstmc.protocol.adventure.BedrockLegacyTextSerializer;
import org.cloudburstmc.protocol.adventure.BedrockLegacyTextSerializer.BedrockNamedTextColor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddPlayerSerializer_v557;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.TextSerializer_v685;
import org.cloudburstmc.protocol.bedrock.codec.v776.Bedrock_v776;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.BuildPlatform;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class TextSerializationTest {
    private static final BedrockLegacyTextSerializer SERIALIZER = BedrockLegacyTextSerializer.getInstance();
    private static final BedrockCodecHelper CODEC_HELPER = Bedrock_v776.CODEC.createHelper();
    private static final TextSerializer_v685 TEXT_SERIALIZER = TextSerializer_v685.INSTANCE;
    private static final AddPlayerSerializer_v557 PLAYER_SERIALIZER = new AddPlayerSerializer_v557();

    static {
        CODEC_HELPER.setTextConverter(new AdventureTextConverter());
    }

    private static String serialize(Component component) {
        return SERIALIZER.serialize(component);
    }

    private static Component deserialize(String text) {
        return SERIALIZER.deserialize(text);
    }

    private static String roundTrip(String legacyText) {
        return serialize(deserialize(legacyText));
    }

    private static TextPacket systemTextPacket(BedrockComponent message) {
        TextPacket packet = new TextPacket();
        packet.setType(TextPacket.Type.SYSTEM);
        packet.setMessage(message);
        packet.setSourceName("");
        packet.setXuid("");
        packet.setPlatformChatId("");
        return packet;
    }

    private static TextPacket translationTextPacket(BedrockComponent message) {
        TextPacket packet = new TextPacket();
        packet.setType(TextPacket.Type.TRANSLATION);
        packet.setMessage(message);
        packet.setSourceName("");
        packet.setXuid("");
        packet.setPlatformChatId("");
        return packet;
    }

    @Test
    public void testDeserializeProducesCorrectComponentTree() {
        String legacyText = "§c%accessibility.play.editRealm Not translated - §e%record.nowPlaying";
        Component result = deserialize(legacyText);
        List<Component> components = StreamSupport.stream(
                        result.iterable(ComponentIteratorType.DEPTH_FIRST).spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(5, components.size());
        assertInstanceOf(TextComponent.class, components.get(0));
        assertInstanceOf(TextComponent.class, components.get(1));
        assertInstanceOf(TranslatableComponent.class, components.get(2));
        assertInstanceOf(TextComponent.class, components.get(3));
        assertInstanceOf(TranslatableComponent.class, components.get(4));
    }

    @Test
    public void testDeserializeRoundTrip() {
        String legacyText = "§c%accessibility.play.editRealm Not translated - §e%record.nowPlaying";
        assertEquals(legacyText, roundTrip(legacyText));
    }

    @Test
    public void testSerializeTranslatableComponentTree() {
        String expected = "§c%accessibility.play.editRealm Not translated - §e%record.nowPlaying";
        Component component = Component.text().style(Style.style(NamedTextColor.RED))
                .append(Component.translatable("accessibility.play.editRealm"))
                .append(Component.text(" Not translated - "))
                .append(Component.translatable("record.nowPlaying").style(Style.style(NamedTextColor.YELLOW)))
                .build();

        assertEquals(expected, serialize(component));
    }

    @Test
    public void testSystemMessageNetworkRoundTrip() {
        String legacyText = "§c%accessibility.play.editRealm Not translated - §e%record.nowPlaying";
        BedrockComponent message = new BedrockComponent(deserialize(legacyText));

        ByteBuf buf = Unpooled.buffer();
        TEXT_SERIALIZER.serialize(buf, CODEC_HELPER, systemTextPacket(message));

        TextPacket out = new TextPacket();
        TEXT_SERIALIZER.deserialize(buf, CODEC_HELPER, out);

        assertEquals(legacyText, serialize(out.getMessage(BedrockComponent.class).asComponent()));
    }

    @Test
    public void testTranslationArgumentsNetworkRoundTrip() {
        BedrockComponent message = new BedrockComponent(
                Component.translatable("record.nowPlaying", Component.text("%item.record_11.desc", NamedTextColor.RED)));

        ByteBuf buf = Unpooled.buffer();
        TEXT_SERIALIZER.serialize(buf, CODEC_HELPER, translationTextPacket(message));

        TextPacket out = new TextPacket();
        TEXT_SERIALIZER.deserialize(buf, CODEC_HELPER, out);

        assertEquals(message, out.getMessage(BedrockComponent.class));
    }

    @Test
    public void testResetInjectedAfterBold() {
        Component component = Component.text("Before ", NamedTextColor.RED)
                .append(Component.text("Bold ", NamedTextColor.YELLOW, TextDecoration.BOLD))
                .append(Component.text("After", NamedTextColor.GREEN));

        assertEquals("§cBefore §e§lBold §r§aAfter", serialize(component));
    }

    @Test
    public void testResetInjectedAfterObfuscated() {
        Component component = Component.text("Before ", NamedTextColor.RED)
                .append(Component.text("Obfuscated ", NamedTextColor.YELLOW, TextDecoration.OBFUSCATED))
                .append(Component.text("After", NamedTextColor.GREEN));

        assertEquals("§cBefore §e§kObfuscated §r§aAfter", serialize(component));
    }

    @Test
    public void testResetInjectedAfterItalic() {
        Component component = Component.text("Before ", NamedTextColor.RED)
                .append(Component.text("Italic ", NamedTextColor.YELLOW, TextDecoration.ITALIC))
                .append(Component.text("After", NamedTextColor.GREEN));

        assertEquals("§cBefore §e§oItalic §r§aAfter", serialize(component));
    }

    @Test
    public void testResetNotInjectedWithoutPrecedingDecoration() {
        Component component = Component.text("Red ", NamedTextColor.RED)
                .append(Component.text("Green", NamedTextColor.GREEN));

        assertEquals("§cRed §aGreen", serialize(component));
    }

    @Test
    public void testResetInjectedAfterMultipleDecorations() {
        Component component = Component.text("A ", NamedTextColor.RED)
                .append(Component.text("Bold ", NamedTextColor.YELLOW, TextDecoration.BOLD, TextDecoration.OBFUSCATED, TextDecoration.ITALIC))
                .append(Component.text("continue ", NamedTextColor.GREEN))
                .append(Component.text("non-reset ", NamedTextColor.AQUA))
                .append(Component.text("italic ").decorate(TextDecoration.ITALIC))
                .append(Component.text("empty"));

        assertEquals("§cA §e§k§l§oBold §r§acontinue §bnon-reset §c§oitalic §r§cempty", serialize(component));
    }

    @Test
    public void testResetInjectedBeforeBedrockMaterialColor() {
        Component component = Component.text("Bold ", NamedTextColor.YELLOW, TextDecoration.BOLD)
                .append(Component.text("Resin", BedrockNamedTextColor.MATERIAL_RESIN));

        assertEquals("§e§lBold §r§v§lResin", serialize(component));
    }

    @Test
    public void testStandardColorsUnchanged() {
        Component component = Component.text("Black ", NamedTextColor.BLACK)
                .append(Component.text("DarkBlue ", NamedTextColor.DARK_BLUE))
                .append(Component.text("DarkGreen ", NamedTextColor.DARK_GREEN))
                .append(Component.text("DarkAqua ", NamedTextColor.DARK_AQUA))
                .append(Component.text("DarkRed ", NamedTextColor.DARK_RED))
                .append(Component.text("DarkPurple ", NamedTextColor.DARK_PURPLE))
                .append(Component.text("Gold ", NamedTextColor.GOLD))
                .append(Component.text("Gray ", NamedTextColor.GRAY))
                .append(Component.text("DarkGray ", NamedTextColor.DARK_GRAY))
                .append(Component.text("Blue ", NamedTextColor.BLUE))
                .append(Component.text("Green ", NamedTextColor.GREEN))
                .append(Component.text("Aqua ", NamedTextColor.AQUA))
                .append(Component.text("Red ", NamedTextColor.RED))
                .append(Component.text("LightPurple ", NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("Yellow ", NamedTextColor.YELLOW))
                .append(Component.text("White", NamedTextColor.WHITE));

        String expected = "§0Black §1DarkBlue §2DarkGreen §3DarkAqua §4DarkRed §5DarkPurple §6Gold §7Gray §8DarkGray §9Blue §aGreen §bAqua §cRed §dLightPurple §eYellow §fWhite";
        assertEquals(expected, serialize(component));
        assertEquals(expected, roundTrip(expected));
    }

    @Test
    public void testAllBedrockMaterialColorsSerialization() {
        Component component = Component.text("Minecoin ", BedrockNamedTextColor.MINECOIN_GOLD)
                .append(Component.text("Quartz ", BedrockNamedTextColor.MATERIAL_QUARTZ))
                .append(Component.text("Iron ", BedrockNamedTextColor.MATERIAL_IRON))
                .append(Component.text("Netherite ", BedrockNamedTextColor.MATERIAL_NETHERITE))
                .append(Component.text("Redstone ", BedrockNamedTextColor.MATERIAL_REDSTONE))
                .append(Component.text("Copper ", BedrockNamedTextColor.MATERIAL_COPPER))
                .append(Component.text("Gold ", BedrockNamedTextColor.MATERIAL_GOLD))
                .append(Component.text("Emerald ", BedrockNamedTextColor.MATERIAL_EMERALD))
                .append(Component.text("Diamond ", BedrockNamedTextColor.MATERIAL_DIAMOND))
                .append(Component.text("Lapis ", BedrockNamedTextColor.MATERIAL_LAPIS))
                .append(Component.text("Amethyst ", BedrockNamedTextColor.MATERIAL_AMETHYST))
                .append(Component.text("Resin", BedrockNamedTextColor.MATERIAL_RESIN));

        String expected = "§gMinecoin §hQuartz §iIron §jNetherite §mRedstone §nCopper §pGold §qEmerald §sDiamond §tLapis §uAmethyst §vResin";
        assertEquals(expected, serialize(component));
        assertEquals(expected, roundTrip(expected));
    }

    @Test
    public void testStrikethroughAbsentFromBedrockFormats() {
        String withStrikethrough = "§mShould not strikethrough";
        Component deserialized = deserialize(withStrikethrough);
        assertFalse(deserialized.hasDecoration(TextDecoration.STRIKETHROUGH), "§m must not map to strikethrough on Bedrock; it is material_redstone");
    }

    @Test
    public void testUnderlineAbsentFromBedrockFormats() {
        String withUnderline = "§nShould not underline";
        Component deserialized = deserialize(withUnderline);
        assertFalse(deserialized.hasDecoration(TextDecoration.UNDERLINED), "§n must not map to underline on Bedrock; it is material_copper");
    }

    @Test
    public void testBedrockComponentSurvivesAddPlayerPacketRoundTrip() {
        AddPlayerPacket packet = new AddPlayerPacket();
        packet.setUsername("Test");
        packet.setRuntimeEntityId(0);
        packet.setUniqueEntityId(0);
        packet.setUuid(new UUID(0, 0));
        packet.setPosition(Vector3f.ZERO);
        packet.setMotion(Vector3f.ZERO);
        packet.setRotation(Vector3f.ZERO);
        packet.setDeviceId("");
        packet.setHand(ItemData.AIR);
        packet.setBuildPlatform(BuildPlatform.GOOGLE);
        packet.setGameType(GameType.SURVIVAL);
        packet.setPlatformChatId("");

        AbilityLayer layer = new AbilityLayer();
        layer.setLayerType(AbilityLayer.Type.BASE);
        layer.setWalkSpeed(0.0F);
        layer.setFlySpeed(0.0F);
        layer.getAbilitiesSet().addAll(Arrays.asList(Ability.values()));
        layer.getAbilityValues().add(Ability.BUILD);
        layer.getAbilityValues().add(Ability.MINE);
        layer.getAbilityValues().add(Ability.DOORS_AND_SWITCHES);
        packet.getAbilityLayers().add(layer);

        packet.getMetadata().put(EntityDataTypes.NAME, new BedrockComponent(Component.text("Custom Name")));
        packet.getMetadata().put(EntityDataTypes.NAMETAG_ALWAYS_SHOW, (byte) 1);
        packet.getMetadata().putFlags(EntityDataMap.flagsOf(EntityFlag.SILENT));

        ByteBuf buf = Unpooled.buffer();
        PLAYER_SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        AddPlayerPacket out = new AddPlayerPacket();
        PLAYER_SERIALIZER.deserialize(buf, CODEC_HELPER, out);

        assertEquals(Component.text("Custom Name"), out.getMetadata().get(EntityDataTypes.NAME, BedrockComponent.class).asComponent());
    }
}
