package org.cloudburstmc.protocol.bedrock;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.netty.channel.TransportDisconnectReason;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@UtilityClass
public class BedrockDisconnectReasons {

    public static final String DISCONNECTED = "disconnect.disconnected";
    public static final String CLOSED = "disconnect.closed";
    public static final String REMOVED = "disconnect.removed";
    public static final String TIMEOUT = "disconnect.timeout";
    public static final String UNKNOWN = "disconnect.lost";

    private static final Map<TransportDisconnectReason, String> FROM_TRANSPORT = generateMappings();

    private static Map<TransportDisconnectReason, String> generateMappings() {
        EnumMap<TransportDisconnectReason, String> map = new EnumMap<>(TransportDisconnectReason.class);
        map.put(TransportDisconnectReason.CLOSED_BY_REMOTE_PEER, CLOSED);
        map.put(TransportDisconnectReason.DISCONNECTED, DISCONNECTED);
        map.put(TransportDisconnectReason.TIMED_OUT, TIMEOUT);
        map.put(TransportDisconnectReason.BAD_PACKET, REMOVED);

        return Collections.unmodifiableMap(map);
    }

    public static String getReason(TransportDisconnectReason reason) {
        return FROM_TRANSPORT.getOrDefault(reason, reason.name());
    }
}
