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
import org.cloudburstmc.protocol.adventure.AdventureTextConverter;
import org.cloudburstmc.protocol.adventure.BedrockComponent;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.adventure.BedrockLegacyTextSerializer;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddPlayerSerializer_v557;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.TextSerializer_v685;
import org.cloudburstmc.protocol.bedrock.codec.v776.Bedrock_v776;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TextSerializationTest {
    private static final BedrockCodecHelper CODEC_HELPER = Bedrock_v776.CODEC.createHelper();
    private static final TextSerializer_v685 TEXT_SERIALIZER = TextSerializer_v685.INSTANCE;
    private static final AddPlayerSerializer_v557 PLAYER_SERIALIZER = new AddPlayerSerializer_v557();

    static {
        CODEC_HELPER.setTextConverter(new AdventureTextConverter());
    }

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
        BedrockComponent result = new BedrockComponent(BedrockLegacyTextSerializer.getInstance().deserialize(legacyText));

        TextPacket packet = new TextPacket();
        packet.setType(TextPacket.Type.SYSTEM);
        packet.setMessage(result);
        packet.setSourceName("");
        packet.setXuid("");
        packet.setPlatformChatId("");

        ByteBuf buf = Unpooled.buffer();
        TEXT_SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        TextPacket deserializedPacket = new TextPacket();
        TEXT_SERIALIZER.deserialize(buf, CODEC_HELPER, deserializedPacket);

        List<Component> components = StreamSupport.stream(result.asComponent().iterable(ComponentIteratorType.DEPTH_FIRST).spliterator(), false).collect(Collectors.toList());

        assertEquals(5, components.size());
        assertInstanceOf(TextComponent.class, components.get(0));
        assertInstanceOf(TextComponent.class, components.get(1));
        assertInstanceOf(TranslatableComponent.class, components.get(2));
        assertInstanceOf(TextComponent.class, components.get(3));
        assertInstanceOf(TranslatableComponent.class, components.get(4));

        BedrockComponent deserializedMessage = deserializedPacket.getMessage(BedrockComponent.class);
        String serializedComponent = BedrockLegacyTextSerializer.getInstance().serialize(deserializedMessage.asComponent());
        assertEquals(legacyText, serializedComponent);
    }

    @Test
    public void testLegacyTranslationArguments() {
        BedrockComponent translatable = new BedrockComponent(Component.translatable("record.nowPlaying", Component.text("%item.record_11.desc", NamedTextColor.RED)));

        TextPacket packet = new TextPacket();
        packet.setType(TextPacket.Type.TRANSLATION);
        packet.setMessage(translatable);
        packet.setSourceName("");
        packet.setXuid("");
        packet.setPlatformChatId("");

        ByteBuf buf = Unpooled.buffer();
        TEXT_SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        TextPacket deserializedPacket = new TextPacket();
        TEXT_SERIALIZER.deserialize(buf, CODEC_HELPER, deserializedPacket);

        BedrockComponent deserializedMessage = deserializedPacket.getMessage(BedrockComponent.class);

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

    @Test
    public void testComplexMetadataSerialization() {
        UUID uuid = new UUID(0, 0);

        AddPlayerPacket packet = new AddPlayerPacket();
        packet.setUsername("Test Username");
        packet.setRuntimeEntityId(0);
        packet.setUniqueEntityId(0);
        packet.setUuid(uuid);
        packet.setPosition(Vector3f.ZERO);
        packet.setMotion(Vector3f.ZERO);
        packet.setRotation(Vector3f.ZERO);
        packet.setDeviceId("");
        packet.setHand(ItemData.AIR);
        packet.setGameType(GameType.SURVIVAL);

        packet.setUuid(uuid);
        packet.setPlatformChatId("");
        packet.setDeviceId(Integer.toString(0));

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
        packet.getMetadata().put(EntityDataTypes.SCALE, 1.0f);

        packet.getMetadata().put(EntityDataTypes.WIDTH, 1.8f);
        packet.getMetadata().put(EntityDataTypes.HEIGHT, 0.6f);
        packet.getMetadata().put(EntityDataTypes.NAMETAG_ALWAYS_SHOW, (byte) 0);

        packet.getMetadata().putFlags(EntityDataMap.flagsOf(EntityFlag.SILENT));

        ByteBuf buf = Unpooled.buffer();
        PLAYER_SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        AddPlayerPacket deserializedPacket = new AddPlayerPacket();
        PLAYER_SERIALIZER.deserialize(buf, CODEC_HELPER, deserializedPacket);

        BedrockComponent bedrockComponent = deserializedPacket.getMetadata().get(EntityDataTypes.NAME, BedrockComponent.class);
        assertEquals(Component.text("Custom Name"), bedrockComponent.asComponent());
    }
}
