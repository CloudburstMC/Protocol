package org.cloudburstmc.protocol.bedrock;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.ReferenceCountUtil;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket;
import org.cloudburstmc.protocol.bedrock.packet.SubClientLoginPacket;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BedrockPeerSubClientTest {

    private final EmbeddedChannel channel = new EmbeddedChannel();
    private final List<Integer> created = new ArrayList<>();
    private final List<String> received = new ArrayList<>();

    @AfterEach
    void tearDown() {
        this.channel.finishAndReleaseAll();
    }

    @Test
    public void serverDropsPacketsToUnknownSubClients() {
        BedrockPeer peer = peer(PacketDirection.CLIENT_BOUND);

        receive(peer, 1, new RequestNetworkSettingsPacket());
        receive(peer, 2, new RequestNetworkSettingsPacket());

        assertTrue(this.created.isEmpty());
        assertTrue(this.received.isEmpty());
        assertFalse(peer.sessions.containsKey(1));
    }

    @Test
    public void serverOpensSubClientSessionOnItsLogin() {
        BedrockPeer peer = peer(PacketDirection.CLIENT_BOUND);

        receive(peer, 1, new SubClientLoginPacket());
        receive(peer, 1, new RequestNetworkSettingsPacket());

        assertEquals(Arrays.asList(1), this.created);
        assertEquals(Arrays.asList("1:SubClientLoginPacket", "1:RequestNetworkSettingsPacket"), this.received);
    }

    @Test
    public void serverOpensPrimarySessionForAnyPacket() {
        BedrockPeer peer = peer(PacketDirection.CLIENT_BOUND);

        receive(peer, 0, new RequestNetworkSettingsPacket());

        assertEquals(Arrays.asList(0), this.created);
        assertEquals(Arrays.asList("0:RequestNetworkSettingsPacket"), this.received);
    }

    @Test
    public void clientStillOpensSubClientSessionsOnDemand() {
        BedrockPeer peer = peer(PacketDirection.SERVER_BOUND);

        receive(peer, 2, new RequestNetworkSettingsPacket());

        assertEquals(Arrays.asList(2), this.created);
        assertEquals(Arrays.asList("2:RequestNetworkSettingsPacket"), this.received);
    }

    private BedrockPeer peer(PacketDirection direction) {
        this.channel.attr(PacketDirection.ATTRIBUTE).set(direction);
        return new BedrockPeer(this.channel, (peer, subClientId) -> {
            this.created.add(subClientId);
            BedrockServerSession session = new BedrockServerSession(peer, subClientId);
            session.setPacketHandler(new BedrockPacketHandler() {
                @Override
                public PacketSignal handlePacket(BedrockPacket packet) {
                    received.add(subClientId + ":" + packet.getClass().getSimpleName());
                    return PacketSignal.HANDLED;
                }
            });
            return session;
        });
    }

    private static void receive(BedrockPeer peer, int targetSubClientId, BedrockPacket packet) {
        BedrockPacketWrapper wrapper = BedrockPacketWrapper.create(0, 0, targetSubClientId, packet, null);
        try {
            peer.onBedrockPacket(wrapper);
        } finally {
            ReferenceCountUtil.release(wrapper);
        }
    }
}
