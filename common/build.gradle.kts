dependencies {
    api(libs.netty.buffer)
    api(enforcedPlatform(libs.fastutil.bom))
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

// https://docs.gradle.org/8.0.1/userguide/publishing_setup.html#sec:suppressing_validation_errors
tasks.withType<GenerateModuleMetadata> {
    suppressedValidationErrors.add("enforced-platform")
}