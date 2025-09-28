package org.cloudburstmc.protocol.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Convert a string into a specific type for use in
 *
 */
public interface TextConverter {

    Default DEFAULT = new Default();

    default CharSequence deserialize(String text) {
        return deserialize(text, true);
    }

    CharSequence deserialize(String text, boolean translatable);

    CharSequence deserializeJson(String text, boolean translatable);

    CharSequence deserializeWithArguments(String text, List<String> parameters, boolean translatable);

    String serialize(CharSequence text);

    String serializeJson(CharSequence text);

    String serializeWithArguments(CharSequence text, List<String> parameters);

    @Nullable
    Boolean needsTranslation(CharSequence text);

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    final class Default implements TextConverter {

        @Override
        public CharSequence deserialize(String text, boolean translatable) {
            return text;
        }

        @Override
        public CharSequence deserializeJson(String text, boolean translatable) {
            return text;
        }

        @Override
        public CharSequence deserializeWithArguments(String text, List<String> parameters, boolean translatable) {
            return text;
        }

        @Override
        public String serialize(CharSequence text) {
            return text.toString();
        }

        @Override
        public String serializeJson(CharSequence text) {
            return text.toString();
        }

        @Override
        public String serializeWithArguments(CharSequence text, List<String> parameters) {
            return text.toString();
        }

        @Override
        public Boolean needsTranslation(CharSequence text) {
            return null;
        }
    }
}
