package org.cloudburstmc.protocol.java.data;

import com.nukkitx.math.vector.Vector2f;
import lombok.Value;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Value
public class DisplayInfo {
    Component title;
    Component description;
    ItemStack icon;
    Key backgroundTexture;
    FrameType frameType;
    boolean toast;
    boolean hidden;
    Vector2f pos;

    public enum FrameType {
        TASK,
        CHALLENGE,
        GOAL;

        private static final FrameType[] VALUES = values();

        public static FrameType getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
}
