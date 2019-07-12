# Protocol

### Introduction

A protocol library for Minecraft that supports multiple versions. (Currently Bedrock Edition only)

### Links

__[Jenkins](https://ci.nukkitx.com/job/NukkitX/job/Protocol/)__

__[JavaDocs](https://ci.nukkitx.com/job/NukkitX/job/Protocol/job/master/javadoc/index.html?overview-summary.html)__

### Usage

#### Client

##### Creating a client
```java
// This is the local address to bind to, not the remote one.
// If bound to 127.0.0.1 any incoming packets from outside your computer will not be received.
InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", 12345);
BedrockClient client = new BedrockClient(bindAddress);

// Bind the port
client.bind().join();
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
});
```

##### Connecting to a Remote Server

A `BedrockClient` can only have one session per instance. If you need more, create extra instances.

```java
InetSocketAddress addressToConnect = new InetSocketAddress("play.nukkitx.com", 19132);
client.connect(addressToConnect).whenComplete((session, throwable) -> {
    if (throwable != null) {
        // Unable to establish connection
        return;
    }
    // Connection established
    // Make sure to set the packet codec version you wish to use before sending out packets
    session.setPacketCodec(Bedrock_v354.V354_CODEC);
    // Add disconnect handler
    session.addDisconnectHandler(() -> System.out.println("Disconnected"));
    // Remember to set a packet handler so you receive incoming packets
    session.setPacketHandler(new FooBarPacketHandler());
    // Now send packets...
});
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
pong.setProtocolVersion(Bedrock_v354.V354_CODEC.getProtocolVersion());

server.setHandler(new BedrockServerEventHandler() {
    @Override
    public boolean onConnectionRequest(InetSocketAddress address) {
        return true; // Connection will be accepted
    }
    
    @Nullable
    @Override
    public BedrockPong onQuery(InetSocketAddress address) {
        return pong;
    }
    
    @Override
    public void onSessionCreation(BedrockServerSession serverSession) {
        // Connection established
        // Add disconnect handler
        session.addDisconnectHandler(() -> System.out.println("Disconnected"));
        // Remember to set a packet handler so you receive incoming packets
        session.setPacketHandler(new FooBarPacketHandler());
        // By default, the server will use a compatible codec that will read any LoginPacket.
        // After receiving the LoginPacket, you need to set the correct packet codec for the client and continue. 
    }
});

// Start server up
server.bind().join();
```

### Maven

##### Protocol Versions:

| Dependency | Minecraft Version |
|:-:|:-:|
| bedrock-v291 | 1.7.0 |
| bedrock-v313 | 1.8.0 |
| bedrock-v332 | 1.9.0 |
| bedrock-v340 | 1.10.0 |
| bedrock-v354 | 1.11.0 |
| bedrock-v361 | 1.12.0 |

##### Repository:

```xml
    <repositories>
        <repository>
            <id>nukkitx-repo</id>
            <url>https://repo.nukkitx.com/maven-releases/</url>
        </repository>
    </repositories>
```

##### Dependencies:

```xml
    <dependencies>
        <dependency>
            <groupId>com.nukkitx.protocol</groupId>
            <artifactId>bedrock-v(VERSION)</artifactId>
            <version>2.1.1</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```
