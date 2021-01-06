package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetServer;
import com.nukkitx.network.raknet.RakNetServerListener;
import com.nukkitx.network.raknet.RakNetServerSession;
import com.nukkitx.network.util.EventLoops;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializers;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.DatagramPacket;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BedrockServer extends Bedrock {
    private final RakNetServer rakNetServer;
    final Set<BedrockServerSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private BedrockServerEventHandler handler;

    public BedrockServer(InetSocketAddress bindAddress) {
        this(bindAddress, 1);
    }

    public BedrockServer(InetSocketAddress bindAddress, int maxThreads) {
        this(bindAddress, maxThreads, EventLoops.commonGroup());
    }

    public BedrockServer(InetSocketAddress bindAddress, int maxThreads, EventLoopGroup eventLoopGroup) {
        super(eventLoopGroup);
        this.rakNetServer = new RakNetServer(bindAddress, maxThreads, eventLoopGroup);
        this.rakNetServer.setProtocolVersion(-1);
        this.rakNetServer.setListener(new BedrockServerListener());
    }

    public BedrockServerEventHandler getHandler() {
        return this.handler;
    }

    public void setHandler(BedrockServerEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public RakNetServer getRakNet() {
        return this.rakNetServer;
    }

    @Override
    public void close() {
        this.close("disconnect.disconnected");
    }

    public void close(String reason) {
        for (BedrockServerSession session : this.sessions) {
            session.disconnect(reason);
        }
        this.rakNetServer.close();
        this.tickFuture.cancel(false);
    }

    public boolean isClosed() {
        return this.rakNetServer.isClosed();
    }

    @Override
    protected void onTick() {
        for (BedrockServerSession session : sessions) {
            session.tick();
        }
    }

    @ParametersAreNonnullByDefault
    private class BedrockServerListener implements RakNetServerListener {

        @Override
        public boolean onConnectionRequest(InetSocketAddress address) {
            return BedrockServer.this.handler == null || BedrockServer.this.handler.onConnectionRequest(address);
        }

        @Nullable
        @Override
        public byte[] onQuery(InetSocketAddress address) {
            if (BedrockServer.this.handler != null) {
                BedrockPong pong = BedrockServer.this.handler.onQuery(address);
                if (pong != null) {
                    pong.setServerId(BedrockServer.this.rakNetServer.getGuid());
                    return pong.toRakNet();
                }
            }
            return null;
        }

        @Override
        public void onSessionCreation(RakNetServerSession connection) {
            BedrockWrapperSerializer serializer = BedrockWrapperSerializers.getSerializer(connection.getProtocolVersion());
            BedrockServerSession session = new BedrockServerSession(connection, BedrockServer.this.eventLoopGroup.next(), serializer);
            connection.setListener(new BedrockRakNetSessionListener.Server(session, connection, BedrockServer.this));
        }

        @Override
        public void onUnhandledDatagram(ChannelHandlerContext ctx, DatagramPacket packet) {
            if (BedrockServer.this.handler != null) {
                BedrockServer.this.handler.onUnhandledDatagram(ctx, packet);
            }
        }
    }
}
