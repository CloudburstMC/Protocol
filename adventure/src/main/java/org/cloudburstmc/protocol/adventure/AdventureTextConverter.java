package org.cloudburstmc.protocol.adventure;

import net.kyori.adventure.text.*;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONOptions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.common.util.TextConverter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

public class AdventureTextConverter implements TextConverter {

    private static final BedrockLegacyTextSerializer LEGACY_SERIALIZER = BedrockLegacyTextSerializer.getInstance();
    private static final ComponentSerializer<Component, Component, String> JSON_SERIALIZER = JSONComponentSerializer.builder()
            .editOptions(builder -> builder.value(JSONOptions.EMIT_RGB, false))
            .build();
    private static final BedrockComponent EMPTY = new BedrockComponent(Component.empty());

    @Override
    public BedrockComponent deserialize(String text, boolean translatable) {
        if (text.isEmpty()) {
            return EMPTY;
        }

        Component component = deserialize(LEGACY_SERIALIZER, text, translatable);

        return new BedrockComponent(component);
    }

    @Override
    public BedrockComponent deserializeJson(String text, boolean translatable) {
        if (text.isEmpty()) {
            return EMPTY;
        }

        Component component = deserialize(JSON_SERIALIZER, text, translatable);

        return new BedrockComponent(component);
    }

    private Component deserialize(ComponentSerializer<Component, Component, String> serializer,
                                  String text, boolean translatable) {
        Component component = serializer.deserialize(text);
        if (translatable) {
            if (component instanceof TextComponent) {
                TextComponent textComponent = (TextComponent) component;
                String content = textComponent.content();

                // If the content is a text component, but contains no percentage signs
                // treat the whole thing as a translatable string (i.e. record.nowPlaying)
                if (content.matches("^[a-zA-Z0-9_.]+$")) {
                    component = Component.translatable(content).style(component.style());
                }
            }
        } else {
            // We have a translatable component, but it is not supported here. Turn it back
            // into a generic text component with the percentage signs intact
            if (component instanceof TranslatableComponent) {
                TranslatableComponent translatableComponent = (TranslatableComponent) component;
                component = Component.text(translatableComponent.fallback() != null ? requireNonNull(translatableComponent.fallback()) : "%" + translatableComponent.key())
                        .style(translatableComponent.style());
            }
        }
        return component;
    }

    @Override
    public BedrockComponent deserializeWithArguments(String text, List<String> parameters, boolean translatable) {
        Component component = this.deserialize(LEGACY_SERIALIZER, text, translatable);
        if (parameters == null || parameters.isEmpty()) {
            return new BedrockComponent(component);
        }

        List<Component> arguments = new ArrayList<>(parameters.size());
        for (String parameter : parameters) {
            // Assume every argument is a Component
            arguments.add(deserialize(LEGACY_SERIALIZER, parameter, false));
        }

        if (component instanceof TranslatableComponent) {
            TranslatableComponent translatableComponent = (TranslatableComponent) component;
            component = Component.translatable(translatableComponent.key(), translatableComponent.style(), arguments);
        }

        return new BedrockComponent(component);
    }

    @Override
    public String serialize(CharSequence text) {
        if (text instanceof String) {
            return (String) text;
        }
        checkArgument(text);
        return LEGACY_SERIALIZER.serialize(((BedrockComponent) text).asComponent());
    }

    @Override
    public String serializeJson(CharSequence text) {
        if (text instanceof String) {
            return (String) text;
        }
        checkArgument(text);
        return JSON_SERIALIZER.serialize(((BedrockComponent) text).asComponent());
    }

    @Override
    public String serializeWithArguments(CharSequence text, List<String> parameters) {
        if (text instanceof String) {
            return (String) text;
        }
        checkArgument(text);
        Component component = ((BedrockComponent) text).asComponent();
        String message;
        if (component instanceof TranslatableComponent) {
            TranslatableComponent translatableComponent = (TranslatableComponent) component;
            if (translatableComponent.style().isEmpty()) {
                // If the translatable component has no style, we can write it directly
                message = translatableComponent.key();
            } else {
                // If it has a style, we need to serialize it as a full component
                message = this.serialize(text);
            }
        } else {
            message = this.serialize(text);
        }

        List<Object> arguments = new ArrayList<>();
        for (Component next : component.iterable(ComponentIteratorType.DEPTH_FIRST)) {
            if (next instanceof TranslatableComponent) {
                TranslatableComponent translatableComponent = (TranslatableComponent) next;
                for (TranslationArgument argument : translatableComponent.arguments()) {
                    arguments.add(argument.value());
                }
            }
        }

        // Clear out any existing parameters
        parameters.clear();
        for (Object argument : arguments) {
            if (argument instanceof Component) {
                parameters.add(this.serialize(new BedrockComponent((Component) argument)));
            } else {
                // If the argument is not a Component, we assume it's a String
                parameters.add(argument.toString());
            }
        }
        return message;
    }

    @Override
    public @Nullable Boolean needsTranslation(CharSequence text) {
        if (text instanceof String) {
            return null;
        }
        checkArgument(text);
        return ((BedrockComponent) text).asComponent() instanceof TranslatableComponent;
    }

    private static void checkArgument(CharSequence text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        } else if (!(text instanceof BedrockComponent)) {
            throw new IllegalArgumentException("Text is not a BedrockComponent. Was " + text.getClass().getName());
        }
    }
}
