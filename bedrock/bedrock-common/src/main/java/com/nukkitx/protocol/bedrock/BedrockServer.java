package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNet;
import com.nukkitx.network.raknet.RakNetServer;
import com.nukkitx.network.raknet.RakNetServerListener;
import com.nukkitx.network.raknet.RakNetServerSession;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BedrockServer extends Bedrock {
    private final RakNetServer rakNetServer;
    final Set<BedrockServerSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private BedrockServerEventHandler handler;

    public BedrockServer(InetSocketAddress bindAddress) {
        this(bindAddress, 1);
    }

    public BedrockServer(InetSocketAddress bindAddress, int maxThreads) {
        this(bindAddress, maxThreads, Executors.newSingleThreadScheduledExecutor());
    }

    public BedrockServer(InetSocketAddress bindAddress, int maxThreads, ScheduledExecutorService scheduler) {
        this(bindAddress, maxThreads, scheduler, scheduler);
    }

    public BedrockServer(InetSocketAddress bindAddress, int maxThreads, ScheduledExecutorService scheduler, Executor executor) {
        super(scheduler, executor);
        this.rakNetServer = new RakNetServer(bindAddress, maxThreads, scheduler, executor);
        this.rakNetServer.setListener(new BedrockServerListener());
    }

    public BedrockServerEventHandler getHandler() {
        return this.handler;
    }

    public void setHandler(BedrockServerEventHandler handler) {
        this.handler = handler;
    }

    @Override
    RakNet getRakNet() {
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
    }

    @Override
    protected void onTick() {
        for (BedrockServerSession session : sessions) {
            this.executor.execute(session::onTick);
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
        public void onSessionCreation(RakNetServerSession rakNetSession) {
            new BedrockServerSession(rakNetSession, BedrockServer.this);
        }
    }
}
