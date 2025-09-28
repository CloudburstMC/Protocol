package org.cloudburstmc.protocol.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BedrockComponent implements CharSequence, ComponentLike {

    private final Component component;
    private transient volatile String serialized;

    public BedrockComponent(Component component) {
        this.component = component;
    }

    @Override
    public int length() {
        return toString().length();
    }

    @Override
    public char charAt(int index) {
        return toString().charAt(index);
    }

    @Override
    public @NotNull CharSequence subSequence(int start, int end) {
        return toString().subSequence(start, end);
    }

    @Override
    public @NotNull Component asComponent() {
        return component;
    }

    @Override
    public @NotNull String toString() {
        if (serialized == null) {
            return serialized = BedrockLegacyTextSerializer.getInstance().serialize(this.component);
        }
        return serialized;
    }

    public static BedrockComponent of(Component component) {
        return new BedrockComponent(component);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BedrockComponent)) {
            return false;
        }
        return this.component.equals(((BedrockComponent) obj).component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(component);
    }
}
