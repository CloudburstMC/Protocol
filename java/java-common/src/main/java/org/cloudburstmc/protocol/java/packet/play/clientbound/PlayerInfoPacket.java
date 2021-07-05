package org.cloudburstmc.protocol.java.packet.play.clientbound;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.data.GameType;
import org.cloudburstmc.protocol.java.data.profile.GameProfile;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

import javax.annotation.Nullable;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerInfoPacket extends JavaPacket<JavaPlayPacketHandler> {
    private Action action;
    private final List<Entry> entries = new ObjectArrayList<>();

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.PLAYER_INFO;
    }

    public enum Action {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;

        private static final Action[] VALUES = values();

        public static Action getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }

    @Value
    public static class Entry {
        int latency;
        @Nullable GameType gameType;
        GameProfile profile;
        @Nullable Component displayName;
    }
}
