#### Client

##### Connecting to a Remote Server

```java
new Bootstrap()
        .channel(RakChannelFactory.client(NioDatagramChannel.class))
        .group(new NioEventLoopGroup())
        .childHandler(new BedrockClientInitializer() {
            @Override
            protected void initSession(BedrockSession session) {
                // Connection established
                // Make sure to set the packet codec version you wish to use before sending out packets
                session.setPacketCodec(Bedrock_v389.V389_CODEC);
                // Add disconnect handler
                session.addDisconnectHandler((reason) -> System.out.println("Disconnected"));
                // Remember to set a packet handler so you receive incoming packets
                session.setPacketHandler(new FooBarPacketHandler());
                // Now send packets...
            }
        })
        .bind(new InetSocketAddress("example.com", 19132))
        .syncUninterruptibly();
```

##### Pinging a Remote Server

This can be done whilst the client is connected to a server however it cannot be to the connected server.

```java
InetSocketAddress addressToPing = new InetSocketAddress("play.nukkitx.com", 19132);
client.ping(addressToPing).whenComplete((pong, throwable) -> {
    if (throwable != null) {
        // Error occurred or timeout
        return;
    }
    // Pong received.
}).join(); // Join if you do not want this to be handled asynchronously.
```

#### Server

##### Creating a Server

```java
InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", 19132);
BedrockServer server = new BedrockServer(bindAddress);

BedrockPong pong = new BedrockPong();
pong.setEdition("MCPE");
pong.setMotd("My Server");
pong.setPlayerCount(0);
pong.setMaximumPlayerCount(20);
pong.setGameType("Survival");
pong.setProtocolVersion(Bedrock_v389.V389_CODEC.getProtocolVersion());

server.setHandler(new BedrockServerEventHandler() {
    @Override
    public boolean onConnectionRequest(InetSocketAddress address) {
        return true; // Connection will be accepted
    }

    @Override
    public BedrockPong onQuery(InetSocketAddress address) {
        return pong;
    }

    @Override
    public void onSessionCreation(BedrockServerSession serverSession) {
        // Connection established
        // Add disconnect handler
        session.addDisconnectHandler((reason) -> System.out.println("Disconnected"));
        // Remember to set a packet handler so you receive incoming packets
        session.setPacketHandler(new FooBarPacketHandler());
        // By default, the server will use a compatible codec that will read any LoginPacket.
        // After receiving the LoginPacket, you need to set the correct packet codec for the client and continue.
    }
});

// Start server up
server.bind().join();
```
