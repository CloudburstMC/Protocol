package org.cloudburstmc.protocol.bedrock;

import io.netty.util.AttributeKey;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;

@Getter
public enum PacketDirection {
    /**
     * All packets sent by this channel are going to client.
     */
    CLIENT_BOUND(PacketRecipient.SERVER, PacketRecipient.CLIENT),
    /**
     * All packets sent by this channel are going to server.
     */
    SERVER_BOUND(PacketRecipient.CLIENT, PacketRecipient.SERVER);

    public static final AttributeKey<PacketDirection> ATTRIBUTE = AttributeKey.valueOf("packet_direction");

    private final PacketRecipient inbound;
    private final PacketRecipient outbound;

    PacketDirection(PacketRecipient inbound, PacketRecipient outbound) {
        this.inbound = inbound;
        this.outbound = outbound;
    }
}
