# Protocol

[![License](https://img.shields.io/badge/license-apache%202.0-blue.svg)](LICENSE)
[![Build Status](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/badge/icon)](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/)
[![Discord](https://img.shields.io/discord/393465748535640064.svg)](https://discord.gg/seCw62a)

### Introduction

A protocol library for Minecraft that supports multiple versions. (Currently Bedrock Edition only)

### Links

* __[Jenkins](https://ci.opencollab.dev/job/NukkitX/job/Protocol/)__
* __[JavaDocs](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/javadoc/index.html?overview-summary.html)__
* __[Discord](https://discord.gg/seCw62a)__

### Usage

Check out [EXAMPLES.md](EXAMPLES.md) for examples on how to use this library.

### Maven

##### Protocol Versions:

|    Class     |  Minecraft Version  |
|:------------:|:-------------------:|
| Bedrock_v291 |        1.7.0        |
| Bedrock_v313 |        1.8.0        |
| Bedrock_v332 |        1.9.0        |
| Bedrock_v340 |       1.10.0        |
| Bedrock_v354 |       1.11.0        |
| Bedrock_v361 |       1.12.0        |
| Bedrock_v388 |       1.13.0        |
| Bedrock_v389 |  1.14.0 - 1.14.50   |
| Bedrock_v390 |       1.14.60       |
| Bedrock_v407 |  1.16.0 - 1.16.10   |
| Bedrock_v408 |       1.16.20       |
| Bedrock_v419 |      1.16.100       |
| Bedrock_v422 | 1.16.200 - 1.16.201 |
| Bedrock_v428 |      1.16.210       |
| Bedrock_v431 |      1.16.220       |

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
        <groupId>org.cloudburstmc.protocol</groupId>
        <artifactId>bedrock-connection</artifactId>
        <version>3.0.0.Beta1-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

#### Projects Using This Library

* [__Cloudburst__ - A bedrock first server software](https://github.com/CloudburstMC/Server)
* [__ProxyPass__ - Vanilla server man-in-the-middle proxy](https://github.com/CloudburstMC/ProxyPass)
* [__Geyser__ - A bridge between Bedrock and Java Edition](https://github.com/GeyserMC/Geyser)
* [__BedrockConnect__ - Allow Xbox/Switch Bedrock clients to add and join servers](https://github.com/Pugmatt/BedrockConnect)

_If you would like to add your project here, please create a pull request._
