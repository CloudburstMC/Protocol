# Protocol

[![License](https://img.shields.io/badge/license-apache%202.0-blue.svg)](LICENSE)
[![Build Status](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/badge/icon)](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/)
[![Discord](https://img.shields.io/discord/393465748535640064.svg)](https://discord.gg/seCw62a)
[![Version](https://repo.opencollab.dev/api/badge/latest/maven-snapshots/org/cloudburstmc/protocol/bedrock-codec)](https://repo.opencollab.dev/#/maven-snapshots/org/cloudburstmc/protocol)

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
| Bedrock_v440 |       1.17.0        |
| Bedrock_v448 |  1.17.10 - 1.17.11  |
| Bedrock_v465 |  1.17.30 - 1.17.34  |
| Bedrock_v471 |  1.17.40 - 1.17.41  |
| Bedrock_v475 |       1.18.0        |
| Bedrock_v486 |       1.18.10       |
| Bedrock_v503 |       1.18.30       |
| Bedrock_v527 |       1.19.0        |
| Bedrock_v534 |       1.19.10       |
| Bedrock_v544 |       1.19.20       |
| Bedrock_v545 |       1.19.21       |
| Bedrock_v554 |       1.19.30       |
| Bedrock_v557 |       1.19.40       |
| Bedrock_v560 |       1.19.50       |
| Bedrock_v567 |       1.19.60       |
| Bedrock_v568 |  1.19.62 - 1.19.63  |
| Bedrock_v575 |  1.19.70 - 1.19.73  |
| Bedrock_v582 |  1.19.80 - 1.19.83  |
| Bedrock_v589 |  1.20.0  - 1.20.1   |
| Bedrock_v594 |  1.20.10 - 1.20.15  |
| Bedrock_v618 |  1.20.30 - 1.20.32  |
| Bedrock_v622 |  1.20.40 - 1.20.41  |
| Bedrock_v630 |  1.20.50 - 1.20.51  |
| Bedrock_v649 |       1.20.60       |
| Bedrock_v662 |       1.20.70       |
| Bedrock_v671 |       1.20.80       |
| Bedrock_v685 |       1.21.0        |
| Bedrock_v686 |       1.21.2        |
| Bedrock_v712 |       1.21.20       |
| Bedrock_v729 |       1.21.30       |
| Bedrock_v748 |       1.21.40       |

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
