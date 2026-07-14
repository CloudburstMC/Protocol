dependencies {
    api(projects.bedrockCodec)
    api(libs.netty.transport.raknet)
    api(libs.snappy)

    testImplementation(libs.junit)
}

tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "org.cloudburstmc.protocol.bedrock.connection")
    }
}
