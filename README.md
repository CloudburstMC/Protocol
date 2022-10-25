# Protocol

[![License](https://img.shields.io/badge/license-Apache-blue.svg)](LICENSE)
[![Build Status](https://ci.nukkitx.com/job/NukkitX/job/Protocol/job/master/badge/icon)](https://ci.nukkitx.com/job/NukkitX/job/Protocol/job/master/)
[![Discord](https://img.shields.io/discord/393465748535640064.svg)](https://discord.gg/seCw62a)

### Introduction

A protocol library for Minecraft that supports multiple versions. (Currently Bedrock Edition only)

### Links

* __[Jenkins](https://ci.nukkitx.com/job/NukkitX/job/Protocol/)__
* __[JavaDocs](https://ci.nukkitx.com/job/NukkitX/job/Protocol/job/master/javadoc/index.html?overview-summary.html)__
* __[Discord](https://discord.gg/seCw62a)__

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
}).join(); // Join if you do not want this to be handled asynchronously.
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
    session.setPacketCodec(Bedrock_v389.V389_CODEC);
    // Add disconnect handler
    session.addDisconnectHandler((reason) -> System.out.println("Disconnected"));
    // Remember to set a packet handler so you receive incoming packets
    session.setPacketHandler(new FooBarPacketHandler());
    // Now send packets...
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

### Maven

##### Protocol Versions:

|  Dependency  |  Minecraft Version  |
|:------------:|:-------------------:|
| bedrock-v291 |        1.7.0        |
| bedrock-v313 |        1.8.0        |
| bedrock-v332 |        1.9.0        |
| bedrock-v340 |       1.10.0        |
| bedrock-v354 |       1.11.0        |
| bedrock-v361 |       1.12.0        |
| bedrock-v388 |       1.13.0        |
| bedrock-v389 |  1.14.0 - 1.14.50   |
| bedrock-v390 |       1.14.60       |
| bedrock-v407 |  1.16.0 - 1.16.10   |
| bedrock-v408 |       1.16.20       |
| bedrock-v419 |      1.16.100       |
| bedrock-v422 | 1.16.200 - 1.16.201 |
| bedrock-v428 |      1.16.210       |
| bedrock-v431 |      1.16.220       |
| bedrock-v440 |       1.17.0        |
| bedrock-v448 |  1.17.10 - 1.17.11  |
| bedrock-v465 |  1.17.30 - 1.17.34  |
| bedrock-v471 |  1.17.40 - 1.17.41  |
| bedrock-v475 |       1.18.0        |
| bedrock-v486 |       1.18.10       |
| bedrock-v503 |       1.18.30       |
| bedrock-v527 |       1.19.0        |
| bedrock-v534 |       1.19.10       |
| bedrock-v544 |       1.19.20       |
| bedrock-v545 |       1.19.21       |
| bedrock-v554 |       1.19.31       |
| bedrock-v557 |       1.19.40       |

##### Repository:

```xml

<repositories>
    <repository>
        <id>opencollab-releases</id>
        <url>https://repo.opencollab.dev/maven-releases/</url>
    </repository>
    <repository>
        <id>opencollab-snapshots</id>
        <url>https://repo.opencollab.dev/maven-snapshots/</url>
    </repository>
</repositories>
```

##### Dependencies:

```xml
    <dependencies>
        <dependency>
            <groupId>com.nukkitx.protocol</groupId>
            <artifactId>bedrock-v(VERSION)</artifactId>
            <version>2.9.14-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

#### Projects Using This Library

* [__Cloudburst__ - A bedrock first server software](https://github.com/CloudburstMC/Server)
* [__ProxyPass__ - Vanilla server man-in-the-middle proxy](https://github.com/NukkitX/ProxyPass)
* [__Geyser__ - A bridge between Bedrock and Java Edition](https://github.com/GeyserMC/Geyser)
* [__BedrockConnect__ - Allow Xbox/Switch Bedrock clients to add and join servers](https://github.com/Pugmatt/BedrockConnect)

_If you would like to add your project here, please create a pull request._
