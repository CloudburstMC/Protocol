dependencies {
    api(projects.common)
    api(libs.netty.buffer)
    api(libs.fastutil.long.common)
    api(libs.fastutil.long.`object`.maps)
    api(libs.jose.jwt)
    api(libs.nbt)
    api(libs.lmbda)
}

tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "org.cloudburstmc.protocol.bedrock.codec")
    }
}