dependencies {
    api(projects.common)
    api(platform(libs.fastutil.bom))
    api(libs.netty.buffer)
    api(libs.fastutil.long.common)
    api(libs.fastutil.long.`object`.maps)
    api(libs.jose4j)
    api(libs.nbt)
    implementation(libs.jackson.annotations)
}

tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "org.cloudburstmc.protocol.bedrock.codec")
    }
}