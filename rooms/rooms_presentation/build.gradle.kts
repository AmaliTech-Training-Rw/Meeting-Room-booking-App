apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.core_ui))
    "implementation"(project(Modules.roomsDomain))

    "implementation"(Coil.coilCompose)
}