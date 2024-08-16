dependencies {
    api(libs.netty.buffer)
    api(platform(libs.fastutil.bom))
    api(libs.fastutil.int.`object`.maps)
    api(libs.fastutil.`object`.int.maps)
    api(libs.math)
    api(libs.natives)
}

tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "org.cloudburstmc.protocol.common")
    }
}