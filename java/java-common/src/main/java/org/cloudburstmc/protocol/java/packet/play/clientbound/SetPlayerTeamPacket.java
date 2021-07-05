package org.cloudburstmc.protocol.java.packet.play.clientbound;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetPlayerTeamPacket extends JavaPacket<JavaPlayPacketHandler> {
    private String name;
    private Action action;
    private Component displayName;
    private Component prefix;
    private Component suffix;
    private TeamColor color;
    private List<String> players;
    private boolean friendlyFire;
    private boolean seeFriendlyInvisibles;
    private CollisionRule collisionRule;
    private NameTagVisibility nameTagVisibility;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.SET_PLAYER_TEAM;
    }

    public enum Action {
        CREATE,
        REMOVE,
        UPDATE,
        ADD_PLAYER,
        REMOVE_PLAYER;

        private static final Action[] VALUES = values();

        public static Action getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }

    public enum CollisionRule {
        ALWAYS,
        NEVER,
        PUSH_OTHER_TEAMS,
        PUSH_OWN_TEAM;

        private static final CollisionRule[] VALUES = values();

        public static CollisionRule getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }

    public enum NameTagVisibility {
        ALWAYS,
        NEVER,
        HIDE_FOR_OTHER_TEAMS,
        HIDE_FOR_OWN_TEAM;

        private static final NameTagVisibility[] VALUES = values();

        public static NameTagVisibility getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }

    public enum TeamColor {
        BLACK,
        DARK_BLUE,
        DARK_GREEN,
        DARK_AQUA,
        DARK_RED,
        DARK_PURPLE,
        GOLD,
        GRAY,
        DARK_GRAY,
        BLUE,
        GREEN,
        AQUA,
        RED,
        LIGHT_PURPLE,
        YELLOW,
        WHITE,
        OBFUSCATED,
        BOLD,
        STRIKETHROUGH,
        UNDERLINE,
        ITALIC,
        RESET;

        private static final TeamColor[] VALUES = values();

        public static TeamColor getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
}
