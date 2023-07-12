apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainBooking))
    // TODO("remove coil")
    "implementation"(Coil.coilCompose)
}
