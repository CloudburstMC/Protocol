# Protocol

### Introduction

A protocol library for Minecraft that supports multiple versions. (Currently Bedrock Edition only)

### Links

__[Jenkins](https://ci.nukkitx.com/job/NukkitX/job/Protocol/)__

__[JavaDocs](https://ci.nukkitx.com/job/NukkitX/job/Protocol/job/master/javadoc/index.html?overview-summary.html)__

### Maven

##### Repository:

```xml
    <repositories>
        <repository>
            <id>nukkitx-repo</id>
            <url>https://repo.nukkitx.com/snapshot/</url>
        </repository>
    </repositories>
```

##### Dependencies:

```xml
    <dependencies>
        <dependency>
            <groupId>com.nukkitx.protocol</groupId>
            <artifactId>bedrock-v(VERSION)</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```