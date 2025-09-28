package org.cloudburstmc.protocol.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.CharacterAndFormat;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class BedrockLegacyTextSerializer implements ComponentSerializer<Component, Component, String> {
    private static final List<CharacterAndFormat> BEDROCK_FORMATS = bedrockFormats();
    private static final Pattern TRANSLATION_MATCHER = Pattern.compile("%[a-zA-Z0-9_.]+");

    private static final BedrockLegacyTextSerializer INSTANCE = new BedrockLegacyTextSerializer();

    private final LegacyComponentSerializer serializer;

    private BedrockLegacyTextSerializer() {
        this(LegacyComponentSerializer.builder()
                .character(LegacyComponentSerializer.SECTION_CHAR)
                .formats(BEDROCK_FORMATS)
                .flattener(ComponentFlattener.basic().toBuilder()
                        .mapper(TranslatableComponent.class, component -> {
                            String fallback = component.fallback();
                            return fallback != null ? fallback : "%" + component.key();
                        })
                        .build()
                )
                .build()
        );
    }

    public BedrockLegacyTextSerializer(LegacyComponentSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public @NotNull Component deserialize(@NotNull String input) {
        return this.serializer.deserialize(input).replaceText(config -> config
                .match(TRANSLATION_MATCHER)
                .replacement((match, builder) -> {
                    String matched = match.group();
                    String key = matched.substring(1);

                    return Component.translatable(key);
                })
        );
    }

    @Override
    public @NotNull String serialize(@NotNull Component component) {
        String serialize = this.serializer.serialize(component);
        
        if (serialize.length() < 2 || !serialize.contains(String.valueOf(LegacyComponentSerializer.SECTION_CHAR))) {
            return serialize;
        }
        
        StringBuilder builder = new StringBuilder(serialize.length() + 8); // Pre-allocate a bit extra for potential reset codes
        boolean needsReset = false;
        
        for (int i = 0; i < serialize.length(); i++) {
            char stringChar = serialize.charAt(i);
            
            // Only process section characters
            if (stringChar == LegacyComponentSerializer.SECTION_CHAR && i + 1 < serialize.length()) {
                char formatChar = serialize.charAt(i + 1);
                
                // Check for style codes that will need reset
                if (formatChar == 'l' || formatChar == 'k' || formatChar == 'o') {
                    needsReset = true;
                } else if (needsReset) {
                    // Reset the style if we encounter a color code after a style code
                    if (Character.isDigit(formatChar) || (formatChar >= 'a' && formatChar <= 'u')) {
                        builder.append(LegacyComponentSerializer.SECTION_CHAR).append('r');
                        needsReset = false;
                    }
                }
            }
            
            builder.append(stringChar);
        }
        
        return builder.toString();
    }

    private static List<CharacterAndFormat> bedrockFormats() {
        List<CharacterAndFormat> formats = new ArrayList<>(CharacterAndFormat.defaults());

        // The following two do not yet exist on Bedrock - https://bugs.mojang.com/browse/MCPE-41729
        formats.remove(CharacterAndFormat.STRIKETHROUGH);
        formats.remove(CharacterAndFormat.UNDERLINED);

        formats.add(CharacterAndFormat.characterAndFormat('g', BedrockNamedTextColor.MINECOIN_GOLD)); // Minecoin Gold
        // Add the new characters implemented in 1.19.80
        formats.add(CharacterAndFormat.characterAndFormat('h', BedrockNamedTextColor.MATERIAL_QUARTZ)); // Quartz
        formats.add(CharacterAndFormat.characterAndFormat('i', BedrockNamedTextColor.MATERIAL_IRON)); // Iron
        formats.add(CharacterAndFormat.characterAndFormat('j', BedrockNamedTextColor.MATERIAL_NETHERITE)); // Netherite
        formats.add(CharacterAndFormat.characterAndFormat('m', BedrockNamedTextColor.MATERIAL_REDSTONE)); // Redstone
        formats.add(CharacterAndFormat.characterAndFormat('n', BedrockNamedTextColor.MATERIAL_COPPER)); // Copper
        formats.add(CharacterAndFormat.characterAndFormat('p', BedrockNamedTextColor.MATERIAL_GOLD)); // Gold
        formats.add(CharacterAndFormat.characterAndFormat('q', BedrockNamedTextColor.MATERIAL_EMERALD)); // Emerald
        formats.add(CharacterAndFormat.characterAndFormat('s', BedrockNamedTextColor.MATERIAL_DIAMOND)); // Diamond
        formats.add(CharacterAndFormat.characterAndFormat('t', BedrockNamedTextColor.MATERIAL_LAPIS)); // Lapis
        formats.add(CharacterAndFormat.characterAndFormat('u', BedrockNamedTextColor.MATERIAL_AMETHYST)); // Amethyst
        return formats;
    }

    public static BedrockLegacyTextSerializer getInstance() {
        return INSTANCE;
    }

    public static class BedrockNamedTextColor implements TextColor {

        public static final BedrockNamedTextColor MINECOIN_GOLD = new BedrockNamedTextColor("minecoin_gold", TextColor.color(221, 214, 5));
        public static final BedrockNamedTextColor MATERIAL_QUARTZ = new BedrockNamedTextColor("material_quartz", TextColor.color(227, 212, 209));
        public static final BedrockNamedTextColor MATERIAL_IRON = new BedrockNamedTextColor("material_iron", TextColor.color(206, 202, 202));
        public static final BedrockNamedTextColor MATERIAL_NETHERITE = new BedrockNamedTextColor("material_netherite", TextColor.color(68, 58, 59));
        public static final BedrockNamedTextColor MATERIAL_REDSTONE = new BedrockNamedTextColor("material_redstone", TextColor.color(151, 22, 7));
        public static final BedrockNamedTextColor MATERIAL_COPPER = new BedrockNamedTextColor("material_copper", TextColor.color(180, 104, 77));
        public static final BedrockNamedTextColor MATERIAL_GOLD = new BedrockNamedTextColor("material_gold", TextColor.color(222, 177, 45));
        public static final BedrockNamedTextColor MATERIAL_EMERALD = new BedrockNamedTextColor("material_emerald", TextColor.color(17, 160, 54));
        public static final BedrockNamedTextColor MATERIAL_DIAMOND = new BedrockNamedTextColor("material_diamond", TextColor.color(44, 186, 168));
        public static final BedrockNamedTextColor MATERIAL_LAPIS = new BedrockNamedTextColor("material_lapis", TextColor.color(33, 73, 123));
        public static final BedrockNamedTextColor MATERIAL_AMETHYST = new BedrockNamedTextColor("material_amethyst", TextColor.color(154, 92, 198));

        private final String name;
        private final TextColor color;

        public BedrockNamedTextColor(String name, TextColor color) {
            this.name = name;
            this.color = color;
        }

        @Override
        public int value() {
            return color.value();
        }
    }
}
