package org.cloudburstmc.protocol.bedrock;

import io.netty.channel.embedded.EmbeddedChannel;
import org.cloudburstmc.netty.channel.TransportDisconnectReason;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BedrockPeerDisconnectReasonTest {

    @Test
    public void aTransportReasonReachesTheSession() throws Exception {
        BedrockPeer peer = peer();
        BedrockSession session = peer.sessions.get(0);

        peer.userEventTriggered(null, TransportDisconnectReason.TIMED_OUT);

        assertEquals(BedrockDisconnectReasons.TIMEOUT, session.getDisconnectReason());
    }

    @Test
    public void aRemoteThatAbortsHasClosed() throws Exception {
        BedrockPeer peer = peer();
        BedrockSession session = peer.sessions.get(0);

        peer.userEventTriggered(null, TransportDisconnectReason.ABORTED_BY_REMOTE_PEER);

        assertEquals(BedrockDisconnectReasons.CLOSED, session.getDisconnectReason());
    }

    @Test
    public void aReasonWithoutABedrockStringKeepsItsName() throws Exception {
        BedrockPeer peer = peer();
        BedrockSession session = peer.sessions.get(0);

        peer.userEventTriggered(null, TransportDisconnectReason.CONNECTION_LOST);

        assertEquals("CONNECTION_LOST", session.getDisconnectReason());
    }

    @Test
    public void aReasonThisSideGaveIsKept() throws Exception {
        BedrockPeer peer = peer();
        BedrockSession session = peer.sessions.get(0);
        session.setDisconnectReason("Kicked");

        peer.userEventTriggered(null, TransportDisconnectReason.CLOSED_BY_REMOTE_PEER);

        assertEquals("Kicked", session.getDisconnectReason());
    }

    private static BedrockPeer peer() throws Exception {
        BedrockPeer peer = new BedrockPeer(new EmbeddedChannel(), BedrockServerSession::new);
        peer.channelActive(null);
        return peer;
    }
}
