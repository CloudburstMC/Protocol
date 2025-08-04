package org.cloudburstmc.protocol.bedrock;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@UtilityClass
public class BedrockDisconnectReasons {

    public static final Component DISCONNECTED = Component.translatable("disconnect.disconnected");
    public static final Component CLOSED = Component.translatable("disconnect.closed");
    public static final Component REMOVED = Component.translatable("disconnect.removed");
    public static final Component TIMEOUT = Component.translatable("disconnect.timeout");
    public static final Component UNKNOWN = Component.translatable("disconnect.lost");

    private static final Map<RakDisconnectReason, Component> FROM_RAKNET = generateRakNetMappings();

    private static Map<RakDisconnectReason, Component> generateRakNetMappings() {
        EnumMap<RakDisconnectReason, Component> map = new EnumMap<>(RakDisconnectReason.class);
        map.put(RakDisconnectReason.CLOSED_BY_REMOTE_PEER, CLOSED);
        map.put(RakDisconnectReason.DISCONNECTED, DISCONNECTED);
        map.put(RakDisconnectReason.TIMED_OUT, TIMEOUT);
        map.put(RakDisconnectReason.BAD_PACKET, REMOVED);

        return Collections.unmodifiableMap(map);
    }

    public static Component getReason(RakDisconnectReason reason) {
        return FROM_RAKNET.getOrDefault(reason, Component.text(reason.name()));
    }
}
