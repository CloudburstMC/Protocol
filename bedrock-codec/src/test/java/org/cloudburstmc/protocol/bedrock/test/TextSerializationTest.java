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
import org.cloudburstmc.protocol.bedrock.codec.BedrockLegacyTextSerializer;
import org.cloudburstmc.protocol.bedrock.codec.compat.NoopBedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.TextSerializer_v685;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TextSerializationTest {
    private static final NoopBedrockCodecHelper CODEC_HELPER = NoopBedrockCodecHelper.INSTANCE;
    private static final TextSerializer_v685 SERIALIZER = TextSerializer_v685.INSTANCE;

    @Test
    public void testLegacyTranslationSerialization() {
        String legacyText = "§c%accessibility.play.editRealm Not translated - §e%record.nowPlaying";
        Component result = BedrockLegacyTextSerializer.getInstance().deserialize(legacyText);
        List<Component> components = StreamSupport.stream(result.iterable(ComponentIteratorType.DEPTH_FIRST).spliterator(), false).collect(Collectors.toList());

        assertEquals(5, components.size());
        assertInstanceOf(TextComponent.class, components.get(0));
        assertInstanceOf(TextComponent.class, components.get(1));
        assertInstanceOf(TranslatableComponent.class, components.get(2));
        assertInstanceOf(TextComponent.class, components.get(3));
        assertInstanceOf(TranslatableComponent.class, components.get(4));

        String serializedLegacy = BedrockLegacyTextSerializer.getInstance().serialize(result);
        assertEquals(legacyText, serializedLegacy);

        Component component = Component.text().style(Style.style(NamedTextColor.RED))
                .append(Component.translatable("accessibility.play.editRealm"))
                .append(Component.text(" Not translated - "))
                .append(Component.translatable("record.nowPlaying").style(Style.style(NamedTextColor.YELLOW)))
                .build();

        String serializedComponent = BedrockLegacyTextSerializer.getInstance().serialize(component);
        assertEquals(legacyText, serializedComponent);
    }

    @Test
    public void testLegacyTranslationNetworkSerialization() {
        String legacyText = "§c%accessibility.play.editRealm Not translated - §e%record.nowPlaying";
        Component result = BedrockLegacyTextSerializer.getInstance().deserialize(legacyText);

        TextPacket packet = new TextPacket();
        packet.setType(TextPacket.Type.SYSTEM);
        packet.setMessage(result);
        packet.setSourceName("");
        packet.setXuid("");
        packet.setPlatformChatId("");

        ByteBuf buf = Unpooled.buffer();
        SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        TextPacket deserializedPacket = new TextPacket();
        SERIALIZER.deserialize(buf, CODEC_HELPER, deserializedPacket);

        List<Component> components = StreamSupport.stream(result.iterable(ComponentIteratorType.DEPTH_FIRST).spliterator(), false).collect(Collectors.toList());

        assertEquals(5, components.size());
        assertInstanceOf(TextComponent.class, components.get(0));
        assertInstanceOf(TextComponent.class, components.get(1));
        assertInstanceOf(TranslatableComponent.class, components.get(2));
        assertInstanceOf(TextComponent.class, components.get(3));
        assertInstanceOf(TranslatableComponent.class, components.get(4));

        Component deserializedMessage = deserializedPacket.getMessage();
        String serializedComponent = BedrockLegacyTextSerializer.getInstance().serialize(deserializedMessage);
        assertEquals(legacyText, serializedComponent);
    }

    @Test
    public void testLegacyTranslationArguments() {
        Component translatable = Component.translatable("record.nowPlaying", Component.text("%item.record_11.desc", NamedTextColor.RED));

        TextPacket packet = new TextPacket();
        packet.setType(TextPacket.Type.TRANSLATION);
        packet.setMessage(translatable);
        packet.setSourceName("");
        packet.setXuid("");
        packet.setPlatformChatId("");

        ByteBuf buf = Unpooled.buffer();
        SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        TextPacket deserializedPacket = new TextPacket();
        SERIALIZER.deserialize(buf, CODEC_HELPER, deserializedPacket);

        Component deserializedMessage = deserializedPacket.getMessage();

        assertEquals(translatable, deserializedMessage);
    }

    @Test
    public void testResetTextDecorations() {
        String legacyText = "§cA test string §e§lBold §r§acontinue";
        Component component = Component.text("A test string ", NamedTextColor.RED)
                .append(Component.text("Bold ", NamedTextColor.YELLOW, TextDecoration.BOLD))
                .append(Component.text("continue", NamedTextColor.GREEN));

        String serializedComponent = BedrockLegacyTextSerializer.getInstance().serialize(component);

        // Verify that the reset character is added before the green color
        assertEquals(legacyText, serializedComponent);
    }

    @Test
    public void testResetMultiTextDecorations() {
        String legacyText = "§cA test string §e§k§l§oBold §r§acontinue §bnon-reset §c§oitalic §r§cempty";
        Component component = Component.text("A test string ", NamedTextColor.RED)
                .append(Component.text("Bold ", NamedTextColor.YELLOW, TextDecoration.BOLD, TextDecoration.OBFUSCATED, TextDecoration.ITALIC))
                .append(Component.text("continue ", NamedTextColor.GREEN))
                .append(Component.text("non-reset ", NamedTextColor.AQUA))
                .append(Component.text("italic ").decorate(TextDecoration.ITALIC))
                .append(Component.text("empty"));

        String serializedComponent = BedrockLegacyTextSerializer.getInstance().serialize(component);

        // Verify that the reset character is added before the green color
        assertEquals(legacyText, serializedComponent);
    }
}
