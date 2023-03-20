#### Client

##### Connecting to a Remote Server

```java
new Bootstrap()
        .channelFactory(RakChannelFactory.client(NioDatagramChannel.class))
        .group(new NioEventLoopGroup())
        .handler(new BedrockClientInitializer() {
            @Override
            protected void initSession(BedrockClientSession session) {
                // Connection established
                // Make sure to set the packet codec version you wish to use before sending out packets
                session.setCodec(Bedrock_v389.CODEC);
                // Remember to set a packet handler so you receive incoming packets
                session.setPacketHandler(new FooBarPacketHandler());
                // Now send packets...
            }
        })
        .connect(new InetSocketAddress("example.com", 19132))
        .syncUninterruptibly();
```

#### Server

##### Creating a Server

```java
InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", 19132);

BedrockPong pong = new BedrockPong()
        .edition("MCPE")
        .motd("My Server")
        .playerCount(0)
        .maximumPlayerCount(20)
        .gameType("Survival")
        .protocolVersion(Bedrock_v389.CODEC.getProtocolVersion());

new ServerBootstrap()
        .channelFactory(RakChannelFactory.server(NioDatagramChannel.class))
        .option(RakChannelOption.RAK_ADVERTISEMENT, pong.toByteBuf())
        .group(new NioEventLoopGroup())
        .childHandler(new BedrockServerInitializer() {
            @Override
            protected void initSession(BedrockServerSession session) {
                // Connection established
                // Make sure to set the packet codec version you wish to use before sending out packets
                session.setCodec(Bedrock_v389.CODEC);
                // Remember to set a packet handler so you receive incoming packets
                session.setPacketHandler(new FooBarPacketHandler());
                // By default, the server will use a compatible codec that will read any LoginPacket.
                // After receiving the LoginPacket, you need to set the correct packet codec for the client and continue.
            }
        })
        .bind(bindAddress)
        .syncUninterruptibly();
```
