package org.cloudburstmc.protocol.java.packet.play.serverbound;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class RecipeBookSeenRecipePacket extends JavaPacket<JavaPlayPacketHandler> {
    private Key recipe;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.RECIPE_BOOK_SEEN_RECIPE;
    }
}
