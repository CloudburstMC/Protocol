# Protocol

[![License](https://img.shields.io/badge/license-apache%202.0-blue.svg)](LICENSE)
[![Build Status](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/badge/icon)](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/)
[![Discord](https://img.shields.io/discord/393465748535640064.svg)](https://discord.gg/seCw62a)
[![Version](https://repo.opencollab.dev/api/badge/latest/maven-snapshots/org/cloudburstmc/protocol/bedrock-codec)](https://repo.opencollab.dev/#/maven-snapshots/org/cloudburstmc/protocol)

### Introduction

A protocol library for Minecraft that supports multiple versions. (Currently Bedrock Edition only)

### Links

* __[Maven](https://repo.opencollab.dev/#/maven-snapshots/org/cloudburstmc/protocol/bedrock-connection)__
* __[JavaDocs](https://ci.opencollab.dev/job/NukkitX/job/Protocol/job/master/javadoc/index.html?overview-summary.html)__
* __[Discord](https://discord.gg/seCw62a)__

## Usage

Check out [EXAMPLES.md](EXAMPLES.md) for examples on how to use this library.

## Supported Versions

This library supports versions 1.7.0+. You can find more information about the supported versions in the [VERSIONS.md](VERSIONS.md) file.

## Adding to Your Project

Snapshots are available in the OpenCollab Maven repository. You can add the following configuration to your Maven/Gradle project to use this library.

<details open>

<summary><b>Gradle</b></summary>

```kotlin
repositories {
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

dependencies {
    implementation("org.cloudburstmc.protocol:bedrock-connection:3.0.0.Beta6-SNAPSHOT")
}
```
</details>

<details>
<summary><b>Maven</b></summary>

```xml

<repositories>
    <repository>
        <id>opencollab-snapshots</id>
        <url>https://repo.opencollab.dev/maven-snapshots/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>org.cloudburstmc.protocol</groupId>
        <artifactId>bedrock-connection</artifactId>
        <version>3.0.0.Beta6-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```
</details>

#### Projects Using This Library

* [__Cloudburst__ - A bedrock first server software](https://github.com/CloudburstMC/Server)
* [__ProxyPass__ - Vanilla server man-in-the-middle proxy](https://github.com/CloudburstMC/ProxyPass)
* [__Geyser__ - A bridge between Bedrock and Java Edition](https://github.com/GeyserMC/Geyser)
* [__BedrockConnect__ - Allow Xbox/Switch Bedrock clients to add and join servers](https://github.com/Pugmatt/BedrockConnect)

_If you would like to add your project here, please create a pull request._
